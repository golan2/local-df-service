package com.golan.local.dataflow.data.registry;

abstract class DataflowHook {

    private final String identifier;

    public DataflowHook(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return "DataflowHook{" +
                "identifier='" + Util.toStringOrDefaultNull(identifier) + "'}";
    }

}
