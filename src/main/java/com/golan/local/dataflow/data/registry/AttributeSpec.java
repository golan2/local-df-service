package com.golan.local.dataflow.data.registry;

import org.json.JSONObject;

class AttributeSpec extends ValueSpec  {
    private static final String ATTRIBUTE_VALUE_CREATED_AT_FIELD = "created_at";
    private static final String ATTRIBUTE_VALUE_UPDATED_AT_FIELD = "updated_at";

    /**
     * Instantiate an object representing an attribute as defined in the project spec.
     * @param identifier {String} - The identifier of the attribute
     * @param object {JSONObject} - The attribute spec
     */
    public AttributeSpec(String identifier, JSONObject object) {
        super(identifier, object);
    }

    public JSONObject getAsJson(AttributeValue attributeValue) {
        JSONObject result  = new JSONObject();
        result.put(NAME_FIELD, name);
        result.put(UUID_FIELD, uuid);
        result.put(TYPE_FIELD, type.getStreamType());
        result.put(COMMENT_FIELD, comment);

        if (unitExist) {
            JSONObject unit = new JSONObject();
            unit.put(UNIT_NAME_FIELD, unitName);
            unit.put(UNIT_SYMBOL_FIELD, unitSymbol);
            result.put(UNIT_FIELD, unit);
        }
        // fill the information from the attribute value
        if (attributeValue == null || attributeValue.getValue() == null || attributeValue.getValue().isEmpty()) {
            result.put(ATTRIBUTE_VALUE_VALUE_FIELD, JSONObject.NULL);
        } else {
            formatAttributeValue(result, attributeValue);
        }
        if (attributeValue != null && attributeValue.getCreatedAt() != null) {
            result.put(ATTRIBUTE_VALUE_CREATED_AT_FIELD, attributeValue.getCreatedAt());
        } else {
            result.put(ATTRIBUTE_VALUE_CREATED_AT_FIELD, JSONObject.NULL);
        }
        if (attributeValue != null && attributeValue.getUpdatedAt() != null) {
            result.put(ATTRIBUTE_VALUE_UPDATED_AT_FIELD, attributeValue.getUpdatedAt());
        } else {
            result.put(ATTRIBUTE_VALUE_UPDATED_AT_FIELD, JSONObject.NULL);
        }
        return result;
    }

    public JSONObject getAsJsonExternal(AttributeValue attributeValue) {
        JSONObject attribAsJson = getAsJson(attributeValue);
        attribAsJson.remove(UUID_FIELD);
        attribAsJson.remove(COMMENT_FIELD);
        return attribAsJson;
    }

    private void formatAttributeValue(JSONObject result, AttributeValue attributeValue) {
        switch(type) {
            case TEXT:
            case TIMESTAMP:
            case IMAGE:
            case UNKNOWN:
                result.put(ATTRIBUTE_VALUE_VALUE_FIELD, attributeValue.getValue());
                break;
            case NUMBER:
                result.put(ATTRIBUTE_VALUE_VALUE_FIELD, Double.parseDouble(attributeValue.getValue()));
                break;
            case INTEGER:
                result.put(ATTRIBUTE_VALUE_VALUE_FIELD, Integer.parseInt(attributeValue.getValue()));
                break;
            case BOOLEAN:
                result.put(ATTRIBUTE_VALUE_VALUE_FIELD, Boolean.parseBoolean(attributeValue.getValue()));
                break;
            case GEOPOINT:
                JSONObject geopoint = new JSONObject(attributeValue.getValue());
                result.put(ATTRIBUTE_VALUE_VALUE_FIELD, geopoint);
                break;
        }
    }


}
