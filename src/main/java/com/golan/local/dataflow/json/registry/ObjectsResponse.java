package com.golan.local.dataflow.json.registry;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.golan.local.dataflow.json.Meta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "meta",
        "objects"
})
public class ObjectsResponse {
    @JsonProperty("meta")
    private Meta meta;
    @JsonProperty("objects")
    private List<RegObject> objects = null;
    @JsonIgnore
    private Map<String, java.lang.Object> additionalProperties = new HashMap<>();

    @JsonProperty("meta")
    public Meta getMeta() {
        return meta;
    }

    @JsonProperty("meta")
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @JsonProperty("objects")
    public List<RegObject> getObjects() {
        return objects;
    }

    @JsonProperty("objects")
    public void setObjects(List<RegObject> objects) {
        this.objects = objects;
    }

    @JsonAnyGetter
    public Map<String, java.lang.Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, java.lang.Object value) {
        this.additionalProperties.put(name, value);
    }

}

