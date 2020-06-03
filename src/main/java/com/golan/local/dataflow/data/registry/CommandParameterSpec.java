package com.golan.local.dataflow.data.registry;

import com.golan.local.dataflow.controllers.RulesEngineController.DataflowStreamType;
import org.json.JSONObject;

class CommandParameterSpec {

    private static final String NAME_FIELD = "name";
    private static final String TYPE_FIELD = "type";
    private static final String COMMENT_FIELD = "comment";
    private static final String UNIT_FIELD = "unit";
    private static final String UNIT_NAME_FIELD = "name";
    private static final String UNIT_SYMBOL_FIELD = "symbol";

    private final String identifier;
    private final String name;
    private final DataflowStreamType type;
    private final String comment;
    private final boolean unitExist;
    private final String unitName;
    private final String unitSymbol;

    /**
     * Instantiate an object representing a Command Parameter as defined in the project spec.
     * @param identifier {String} - The identifier of the Command Parameter
     * @param object {JSONObject} - The Command Parameter spec
     */
    public CommandParameterSpec(String identifier, JSONObject object) {
        if (object != null) {
            this.identifier = identifier;
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
        return "CommandParameterSpec:{" +
                "identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", comment='" + comment + '\'' +
                ", unitExist=" + unitExist +
                ", unitName='" + unitName + '\'' +
                ", unitSymbol='" + unitSymbol + '\'' +
                '}';
    }
}
