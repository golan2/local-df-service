package com.golan.local.dataflow.data.registry;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

class CommandHook extends  DataflowHook {

    private static final String TYPE_FIELD = "type";
    private static final String DATA_FIELD = "data";

    private final String type;
    private final JSONObject data;
    private final Map<String,String> commandParametersMap = new HashMap<>();

    public CommandHook(String identifier, JSONObject object) {
        super(identifier);

        if (object == null) {
            this.type = null;
            this.data = null;
        } else {
            this.type = object.optString(TYPE_FIELD);
            this.data = object.optJSONObject(DATA_FIELD);
        }
        if (data !=null) {
            data.toMap().forEach((key,value)-> commandParametersMap.put(key,value.toString()));
        }
    }

    public Map<String,String> getCommandParameters() {
        return commandParametersMap;
    }

    public String getCommandParameter(String key) {
        return commandParametersMap.get(key);
    }

    @Override
    public String getType() {
        return type;
    }

    public JSONObject getData() {
        return data;
    }

    @Override
    public String toString() {
        return "CommandHook {" +
                "identifier='" + Util.toStringOrDefaultNull(getIdentifier()) + '\'' +
                ", type='" + Util.toStringOrDefaultNull(type) + '\'' +
                ", data=" + Util.toStringOrDefaultNull(data) +
                '}';
    }

}
