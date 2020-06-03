package com.golan.local.dataflow.data.registry;

import com.golan.local.dataflow.controllers.RulesEngineController.DataflowStreamType;
import org.json.JSONObject;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

class ValueFilter {
    private static final String ERROR_FORMAT_FILTER_TYPE_IS_NOT_IMPLEMENTED = "Filter type '%s' is not implemented";
    private static final String ERROR_FORMAT_FILTER_OR_ATTRIBUTE_IS_NOT_RECOGNIZED = "Unrecognized '%s' name: '%s'";
    private static final String ERROR_FORMAT_UNRECOGNIZED_OPERATOR = "Unrecognized operator: '%s'";
    private static final String ERROR_FORMAT_INCOMPATIBLE_OPERATOR_BY_TYPE = "Operator '%s' isn't compatible with value type: '%s'";
    private static final String ERROR_FORMAT_INCOMPATIBLE_VALUE_BY_TYPE = "The value associated with operator '%s' is not compatible with dataflow type '%s'";

    private final UUID uuid;
    private final String name;
    private final ValueOperator operator;
    private final Object value;
    private final ValueSpec valueSpec;

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public ValueOperator getOperator() {
        return operator;
    }

    public Object getValue() {
        return value;
    }

    public ValueSpec getValueSpec() {
        return valueSpec;
    }

    public DataflowStreamType getValueType() {
        return valueSpec.getType();
    }

    /**
     * create a new ValueFilter instance from the given JSONObject.
     *
     * @param jsonFilter    - format: {"name": {"operator": "value"}}
     *                      where value can be text, number, integer or any other valid Dataflow type, as specified by the ProjectSpec.
     *                      validation: the expected format includes one value name(attribute name or stream name) associated with one json
     *                      that includes one operator associated with one value.
     * @param dataflowClass
     * @param filterType
     * @return new instance of {@link ValueFilter}
     * @throws RuntimeException in case of invalid format or invalid operator name
     */
    public static ValueFilter createFrom(JSONObject jsonFilter, DataflowClass dataflowClass, BodyFilters.FilterType filterType) throws RuntimeException {
        String valueName;
        Object operatorValue;

        //extract value name
        valueName = extractSingleFieldName(jsonFilter);
        JSONObject jsonObject = jsonFilter.getJSONObject(valueName);
        ValueSpec valueSpec;
        switch (filterType) {
            case ATTRIBUTE:
                valueSpec = dataflowClass.getAttribute(valueName);
                break;
            case STREAM:
                valueSpec = dataflowClass.getStream(valueName);
                break;
            default:
                throw new RuntimeException(String.format(ERROR_FORMAT_FILTER_TYPE_IS_NOT_IMPLEMENTED, filterType.getName()));
        }

        if (valueSpec == null) {
            throw new RuntimeException(String.format(ERROR_FORMAT_FILTER_OR_ATTRIBUTE_IS_NOT_RECOGNIZED, filterType.getName(), valueName));
        }

        //extract operator
        String operatorKey = extractSingleFieldName(jsonObject);
        Optional<ValueOperator> optionalOperatorKey = ValueOperator.fromString(operatorKey);
        if (!optionalOperatorKey.isPresent()) {
            throw new RuntimeException(String.format(ERROR_FORMAT_UNRECOGNIZED_OPERATOR, operatorKey));
        }
        ValueOperator operator = optionalOperatorKey.get();
        if (!operator.isCompatibleType(valueSpec.getType())) {
            throw new RuntimeException(String.format(ERROR_FORMAT_INCOMPATIBLE_OPERATOR_BY_TYPE, operator.getValue(), valueSpec.getType().getStreamType()));
        }
        operatorValue = jsonObject.opt(operatorKey);
        operatorValue = valueSpec.convertByType(operatorValue);
        if (operatorValue != null) {
            return new ValueFilter(valueSpec.getUuid(), valueName, operator, operatorValue, valueSpec);
        }
        else {
            throw new RuntimeException(String.format(ERROR_FORMAT_INCOMPATIBLE_VALUE_BY_TYPE,
                    operatorKey, valueSpec.getType().getStreamType()));
        }

    }

    /**
     * @param jsonObject
     * @return
     * @throws RuntimeException in case jsonObject doesn't have exactly one single field
     *                        throws {@link NullPointerException} in case jsonObject is null
     */
    private static String extractSingleFieldName(JSONObject jsonObject) throws RuntimeException {
        if (jsonObject == null) {
            throw new NullPointerException();
        }
        Set<String> keyNames = jsonObject.keySet();
        if (keyNames.size() != 1) {
            throw new RuntimeException("Unrecognized fields: instead of one field, got the following: " + keyNames);
        } else {
            return keyNames.iterator().next();
        }
    }

    private ValueFilter(UUID uuid, String name, ValueOperator operator, Object value, ValueSpec valueSpec) {
        this.uuid = uuid;
        this.name = name;
        this.operator = operator;
        this.value = value;
        this.valueSpec = valueSpec;
    }

    /**
     * format: {"uuid": {"operator": value}}
     *
     * @return
     */
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        JSONObject operatorKeyValue = new JSONObject();
        operatorKeyValue.put(operator.getValue(), value);
        jsonObject.put(uuid.toString(), operatorKeyValue);
        return jsonObject;
    }

    public boolean matches(String value) {
        switch (valueSpec.getType()) {
            case TEXT:
                return isMatchedText(value);
            case GEOPOINT:
                return isMatchedGeoPoint(value);
            case BOOLEAN:
                return isMatchedBoolean(value);
            case INTEGER:
                return isMatchedInteger(value);
            case NUMBER:
                return isMatchedDouble(value);
            case TIMESTAMP:
                return isMatchedTimestamp(value);
            case IMAGE:
                return isMatchedImage(value);
            default:
                return false;
        }
    }

    private boolean isMatchedImage(String value) {
        String image = valueSpec.optConvertAttributeImage(value);
        String imageFilter = valueSpec.optConvertAttributeImage(getValue());
        if (image != null && imageFilter != null) {
            if (getOperator() == ValueOperator.EQUALS) {
                return image.equals(imageFilter);
            } else {
                return false;
            }
        }
        else {
            return false;
        }

    }

    private boolean isMatchedTimestamp(String valueTimestamp) {
        Instant timestamp = valueSpec.optConvertAttributeTimestamp(valueTimestamp);
        Instant timestampFilter = valueSpec.optConvertAttributeTimestamp(getValue());
        if (timestamp == null || timestampFilter == null) {
            return false;
        }
        switch (getOperator()) {
            case EQUALS:
                return timestamp.equals(timestampFilter);
            case LESS_THAN:
                return timestamp.isBefore(timestampFilter);
            case LESS_THAN_EQUALS:
                return timestamp.isBefore(timestampFilter) || timestamp.equals(timestampFilter);
            case GREATER_THAN:
                return timestamp.isAfter(timestampFilter);
            case GREATER_THAN_EQUALS:
                return timestamp.isAfter(timestampFilter) || timestamp.equals(timestampFilter);
            default:
                return false;
        }
    }

    private boolean isMatchedDouble(String valueDouble) {
        Double number = valueSpec.optConvertToDouble(valueDouble);
        Double numberFilter = valueSpec.optConvertToDouble(getValue());
        if (number != null && numberFilter != null) {
            int comparisonResult = number.compareTo(numberFilter);
            return isNumberMatched(comparisonResult);
        } else {
            return false;
        }
    }

    private boolean isMatchedInteger(String intValue) {
        Long integer = valueSpec.optConvertToInteger(intValue);
        Long integerFilter = valueSpec.optConvertToInteger(getValue());
        if (integer != null && integerFilter != null) {
            int comparisonResult = integer.compareTo(integerFilter);
            return isNumberMatched(comparisonResult);
        } else {
            return false;
        }

    }

    private boolean isNumberMatched(int comparisonResult) {
        switch (getOperator()) {
            case EQUALS:
                return (comparisonResult == 0);
            case LESS_THAN:
                return (comparisonResult < 0);
            case LESS_THAN_EQUALS:
                return (comparisonResult <= 0);
            case GREATER_THAN:
                return (comparisonResult > 0);
            case GREATER_THAN_EQUALS:
                return (comparisonResult >= 0);
            default:
                return false;
        }
    }


    private boolean isMatchedBoolean(String value) {
        Optional<Boolean> bool = valueSpec.optConvertToBoolean(value);
        Optional<Boolean> boolFilter = valueSpec.optConvertToBoolean(getValue());
        if (bool.isPresent() && boolFilter.isPresent()) {
            if (getOperator() == ValueOperator.EQUALS){
                return bool.equals(boolFilter);
            }
            else{
                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isMatchedGeoPoint(String value) {
        GeoPoint geopoint = valueSpec.optConvertToGeoPoint(value);
        GeoPoint geoPointFilter = valueSpec.optConvertToGeoPoint(getValue());
        if (geopoint != null && geoPointFilter != null) {
            if (getOperator() == ValueOperator.EQUALS){
                return geopoint.equals(geoPointFilter);
            }
            else{
                return false;
            }
        }
        return false;
    }

    private boolean isMatchedText(String value) {
        String text = valueSpec.optConvertAttributeString(value);
        String textFilter = valueSpec.optConvertAttributeString(getValue());
        if (text == null || textFilter == null) {
            return false;
        }

        switch (getOperator()) {
            case EQUALS:
                return text.equals(textFilter);
            case STARTS_WITH:
                return text.startsWith(textFilter);
            default:
                return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueFilter that = (ValueFilter) o;
        return Objects.equals(uuid, that.uuid) &&
                Objects.equals(name, that.name) &&
                operator == that.operator &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, operator, value);
    }

}
