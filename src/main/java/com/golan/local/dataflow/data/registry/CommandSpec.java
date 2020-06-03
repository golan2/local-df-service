package com.golan.local.dataflow.data.registry;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

class CommandSpec {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String NAME_FIELD = "name";
    private static final String COMMENT_FIELD = "comment";
    private static final String REQUEST_FIELD = "request";
    private static final String RESPONSE_FIELD = "response";

    private final String identifier;
    private final String name;
    private final String comment;
    private final Map<String, CommandParameterSpec> request;
    private final Map<String, CommandParameterSpec> response;

    @Override
    public String toString() {
        return "CommandSpec{" +
                "identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                ", comment='" + comment + '\'' +
                ", request=" + request +
                ", response=" + response +
                '}';
    }

    public CommandSpec(String commandIdentifier, JSONObject commandJson) {
        identifier = commandIdentifier;
        name = commandJson.optString(NAME_FIELD);
        comment = commandJson.optString(COMMENT_FIELD);
        request = serializeParameters(commandJson.optJSONObject(REQUEST_FIELD));
        response = serializeParameters(commandJson.optJSONObject(RESPONSE_FIELD));
    }

    /**
     * Translates a JSON object representing command parameters into a map holding CommandParametersSpec objects
     * @param parameterJson {JSONObject} - Command parameters spec as defined in the project spec YAML
     * @return {Map<String, CommandParameterSpec>}
     */
    private Map<String, CommandParameterSpec> serializeParameters(JSONObject parameterJson) {
        Map<String, CommandParameterSpec> parameterSpecMap = new HashMap<>();
        if (parameterJson != null) {
            for (String parameterName : parameterJson.keySet()) {
                try {
                    CommandParameterSpec parameterSpec = new CommandParameterSpec(parameterName, parameterJson.optJSONObject(parameterName));
                    parameterSpecMap.put(parameterName, parameterSpec);
                } catch (IllegalArgumentException e) {
                    logger.error("CommandSpec fail to create CommandParameterSpec for parameter '{}', fail with '{}', JSON: {}",
                            parameterName, e.getMessage(), parameterJson);
                }
            }
        }
        return parameterSpecMap;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public Map<String, CommandParameterSpec> getRequest() {
        return request;
    }

    public Map<String, CommandParameterSpec> getResponse() {
        return response;
    }


}
