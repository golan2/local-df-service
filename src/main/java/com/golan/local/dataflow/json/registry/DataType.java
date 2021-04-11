package com.golan.local.dataflow.json.registry;


import com.fasterxml.jackson.annotation.JsonProperty;

public enum DataType {
    @JsonProperty("number")
    NUMBER,
    @JsonProperty("text")
    TEXT,
    @JsonProperty("boolean")
    BOOLEAN,
    @JsonProperty("geopoint")
    GEOPOINT,
    @JsonProperty("integer")
    INTEGER,
    @JsonProperty("timestamp")
    TIMESTAMP,
    @JsonProperty("image")
    IMAGE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

}