package com.golan.local.dataflow.data.registry;

import org.json.JSONObject;

class DataflowEvent {

    private final JSONObject value;
    private final String uuid;

    public DataflowEvent(JSONObject object) {
        this.uuid = object.getString("uuid");
        this.value = object.getJSONObject("data");

    }

    public String getTypeValueByKey(String keyEvn) {
        JSONObject stream = value.getJSONObject(keyEvn);
        return stream.optString("type","");
    }

    public String getUUIDValueByKey(String keyEvn) {
        JSONObject stream = value.getJSONObject(keyEvn);
        return stream.optString("uuid","");
    }

    public JSONObject getValue() {
        return value;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return "DataflowEvent{" +
                "value=" + Util.toStringOrDefaultNull(value) +
                ", uuid='" + Util.toStringOrDefaultNull(uuid) + '\'' +
                '}';
    }

}


