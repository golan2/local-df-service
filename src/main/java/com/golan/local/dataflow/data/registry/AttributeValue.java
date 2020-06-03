package com.golan.local.dataflow.data.registry;

import org.json.JSONObject;

import java.util.Date;
import java.util.UUID;

class AttributeValue {

    private static final String IDENTIFIER_FIELD = "identifier";
    private static final String UUID_FIELD = "uuid";
    private static final String VALUE_FIELD = "value";
    private static final String CREATED_AT = "created_at";
    private static final String UPDATED_AT = "updated_at";

    private final String identifier;
    private final UUID uuid;
    private final String value;
    private final String createdAt;
    private final String updatedAt;

    /**
     * Instantiate an object representing a single attribute value
     * @param attribObject {JSONObject} - Attribute values obtained from an external source
     */
    public AttributeValue(JSONObject attribObject) {
        this.identifier = attribObject.optString(IDENTIFIER_FIELD, "");
        String uuidStr = attribObject.optString(UUID_FIELD, "");
        if (uuidStr.isEmpty()) {
            throw new IllegalArgumentException("AttributeValue is missing mandatory value: 'uuid'");
        }
        this.uuid = UUID.fromString(uuidStr);
        this.value = attribObject.optString(VALUE_FIELD, "");
        String created = attribObject.optString(CREATED_AT, "");
        if (created.isEmpty()) {
            this.createdAt = new Date().toInstant().toString();
        }
        else{
            this.createdAt = attribObject.optString(CREATED_AT, "");
        }
        String updated = attribObject.optString(UPDATED_AT, "");
        if (updated.isEmpty()) {
            this.updatedAt = new Date().toInstant().toString();
        }
        else{
            this.updatedAt = attribObject.optString(UPDATED_AT, "");
        }
    }

    /**
     * Class C'tor based on class fields
     *
     * @param identifier {String} - Attribute identifier
     * @param uuid {UUID} - Attribute UUID
     * @param value {String} - Attribute value (after converting to String
     * @param createdAt {String} - Attribute created timestamp
     * @param updatedAt {String} - Attribute last updated timestamp
     */
    public AttributeValue(String identifier, UUID uuid, String value, String createdAt, String updatedAt) {
        this.identifier = identifier;
        this.uuid = uuid;
        this.value = value;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getIdentifier() {
        return identifier;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "AttributeValue{" +
                "identifier='" + Util.toStringOrDefaultNull(identifier) + '\'' +
                ", uuid=" + Util.toStringOrDefaultNull(uuid) + '\'' +
                ", value='" + Util.toStringOrDefaultNull(value) + '\'' +
                ", createdAt='" + Util.toStringOrDefaultNull(createdAt) + '\'' +
                ", updatedAt='" + Util.toStringOrDefaultNull(updatedAt) + '\'' +
                '}';
    }
}
