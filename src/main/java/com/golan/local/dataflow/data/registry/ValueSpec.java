package com.golan.local.dataflow.data.registry;

import com.golan.local.dataflow.controllers.RulesEngineController.DataflowStreamType;
import org.json.JSONObject;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

abstract class ValueSpec {
    private static final String VALID_TIMESTAMP_REGEX = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2]\\d|3[0-1])T(2[0-3]|[01]\\d):[0-5]\\d:[0-5]\\d(.\\d{3})Z";
    private static final Pattern VALID_TIMESTAMP_PATTERN = Pattern.compile(VALID_TIMESTAMP_REGEX);

    protected static final String NAME_FIELD = "name";
    protected static final String TYPE_FIELD = "type";
    protected static final String COMMENT_FIELD = "comment";
    protected static final String UUID_FIELD = "uuid";
    protected static final String UNIT_FIELD = "unit";
    protected static final String UNIT_NAME_FIELD = "name";
    protected static final String UNIT_SYMBOL_FIELD = "symbol";
    protected static final String ATTRIBUTE_VALUE_VALUE_FIELD = "value";

    protected final String identifier;
    protected final UUID uuid;
    protected final String name;
    protected final DataflowStreamType type;
    protected final String comment;
    protected final boolean unitExist;
    protected final String unitName;
    protected final String unitSymbol;

    /**
     * Instantiate an object representing the value specifications as defined in the project spec.
     *
     * @param identifier {String} - The identifier of the attribute
     * @param object     {JSONObject} - The attribute spec
     */
    public ValueSpec(String identifier, JSONObject object) {
        if (object != null) {
            this.identifier = identifier;
            String uuidStr = object.optString(UUID_FIELD, "");
            if (uuidStr.isEmpty()) {
                throw new IllegalArgumentException(this.getClass().getSimpleName() + " is missing mandatory value: 'uuid'");
            }
            uuid = UUID.fromString(uuidStr);
            name = object.optString(NAME_FIELD, "");
            type = DataflowStreamType.create(object.optString(TYPE_FIELD));
            comment = object.optString(COMMENT_FIELD, "");
            JSONObject unit = object.optJSONObject(UNIT_FIELD);
            if (unit != null) {
                unitName = unit.optString(UNIT_NAME_FIELD, "");
                unitSymbol = unit.optString(UNIT_SYMBOL_FIELD, "");
                unitExist = true;
            } else {
                unitName = null;
                unitSymbol = null;
                unitExist = false;
            }
        } else {
            throw new IllegalArgumentException("JSON provided was null");
        }
    }

    /**
     * Convert the value based on this spec type:
     * {@link DataflowStreamType}
     * NUMBER is converted to Double
     * INTEGER is converted to Long
     * IMAGE is converted To String
     * BOOLEAN is converted to Boolean
     * GEOPOINT is converted to GeoPoint
     * TIMESTAMP is converted to Instant
     * @param value
     * @return The value converted to its relevant type.
     * In case, the value cannot be converted, null is returned
     */
    public Object convertByType(Object value) {
        switch (getType()) {
            case NUMBER:
                return optConvertToDouble(value);
            case INTEGER:
                return optConvertToInteger(value);
            case IMAGE:
                return optConvertAttributeImage(value);
            case TEXT:
                return optConvertAttributeString(value);
            case BOOLEAN:
                Optional<Boolean> optionalBoolean = optConvertToBoolean(value);
                return optionalBoolean.orElse(null);
            case GEOPOINT:
                return optConvertToGeoPoint(value);
            case TIMESTAMP:
                return optConvertAttributeTimestamp(value);
            case UNKNOWN:
            default:
                throw new ClassCastException("unknown value type");
        }
    }

    public boolean isValidValue(Object value){
        return (convertByType(value) != null);
    }

    public String optConvertAttributeImage(Object value) {
        if (getType() != DataflowStreamType.IMAGE){
            return null;
        }

        if (value instanceof String && !value.toString().isEmpty()) {
            return value.toString();
        }
        return null;
    }


    public String optConvertAttributeString(Object value) {
        if (getType() != DataflowStreamType.TEXT){
            return null;
        }

        if (value instanceof String) {
            return (String) value;
        }
        return null;
    }


    /**
     * Before calling this method, you must verify with method isValidValue to avoid IllegalArgumentException
     *
     * @param value
     * @return In case the type in this spec is not DataflowStreamType.NUMBER , or the given value cannot be converted to Double,
     * @throws IllegalArgumentException
     */
    public Double optConvertToDouble(Object value) {
        if (getType() == DataflowStreamType.NUMBER) {
            if (value instanceof Number) {
                return  ((Number) value).doubleValue();
            } else if (value instanceof String) {
                return Double.parseDouble(value.toString());
            }
        }
        return null;
    }

    /**
     * IMPORTANT: This method convert a value of Dataflow "integer" type to the corresponding Java type.
     * In our docs "integer" defined as " a 64-bit signed integer", which equivalent to java's long type.
     * That's the reason to the confusing name that may indicate that it converts to "Integer" but it actually converts to long
     *
     * @param value
     * @return
     */
    public Long optConvertToInteger(Object value) {
        if (getType() == DataflowStreamType.INTEGER) {
            if (value instanceof Integer) {
                return Long.valueOf((Integer) value);
            } else if (value instanceof Long) {
                return (Long) value;
            } else if (value instanceof Short) {
                return Long.valueOf((Short) value);
            } else if (value instanceof String) {
                try {
                    return Long.parseLong(value.toString());
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        }
        return null;
    }

    /**
     * Before calling this method, you must verify with method isValidValue to avoid IllegalArgumentException
     *
     * @param value
     * @return In case the type in this spec is not BOOLEAN, or the given value cannot be converted to Boolean,
     * @throws IllegalArgumentException
     */
    public Optional<Boolean> optConvertToBoolean(Object value) {
        if (getType() == DataflowStreamType.BOOLEAN) {
            if (value instanceof Boolean) {
                return Optional.of((Boolean) value);
            } else if (value instanceof String) {
                String str = value.toString();
                if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("false")) {
                    return Optional.of(Boolean.parseBoolean(str));
                }
            }
        }

        return Optional.empty();
    }

    public GeoPoint optConvertToGeoPoint(Object value) {
        if (getType() == DataflowStreamType.GEOPOINT) {
            return GeoPoint.parse(value);
        }
        return null;
    }

    public Instant optConvertAttributeTimestamp(Object value) {
        if (getType() != DataflowStreamType.TIMESTAMP){
            return null;
        }

        if (value instanceof Instant) {
            return (Instant) value;
        } else if (validateAttributeTimestamp(value)) {
            try {
                return ZonedDateTime.parse((String) value).toInstant();
            } catch (Exception e) {
                return null;
            }
        }
        return null;

    }

    private boolean validateAttributeTimestamp(Object value) {
        if (value instanceof String) {
            String timestamp = value.toString();
            if (!timestamp.isEmpty()) {
                if (VALID_TIMESTAMP_PATTERN.matcher(timestamp).matches()) {
                    return true;
                } else {
                    try {
                        ZonedDateTime.parse(timestamp).toInstant();
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public DataflowStreamType getType() {
        return type;
    }

    public String getComment() {
        return comment;
    }

    public boolean isUnitExist() {
        return unitExist;
    }

    public String getUnitName() {
        return unitName;
    }

    public String getUnitSymbol() {
        return unitSymbol;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "identifier='" + identifier + '\'' +
                ", uuid=" + Util.toStringOrDefaultNull(uuid) +
                ", name='" + Util.toStringOrDefaultNull(name) + '\'' +
                ", type=" + Util.toStringOrDefaultNull(type) +
                ", comment='" + Util.toStringOrDefaultNull(comment) + '\'' +
                ", unitExist=" + Util.toStringOrDefaultNull(unitExist) +
                ", unitName='" + Util.toStringOrDefaultNull(unitName) + '\'' +
                ", unitSymbol='" + Util.toStringOrDefaultNull(unitSymbol) + '\'' +
                '}';
    }

}
