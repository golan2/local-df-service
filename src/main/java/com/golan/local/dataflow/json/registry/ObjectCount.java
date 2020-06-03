package com.golan.local.dataflow.json.registry;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@SuppressWarnings("unused")
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"class_name", "objects_count"})
public class ObjectCount {

    @JsonProperty("class_name")
    private String className;
    @JsonProperty("objects_count")
    private Integer objectsCount;
    @JsonIgnore
    private Map<String, Object> additionalProperties;

    @JsonProperty("class_name")
    public String getClassName() {
        return className;
    }

    @JsonProperty("class_name")
    public void setClassName(String className) {
        this.className = className;
    }

    @JsonProperty("objects_count")
    public Integer getObjectsCount() {
        return objectsCount;
    }

    @JsonProperty("objects_count")
    public void setObjectsCount(Integer objectsCount) {
        this.objectsCount = objectsCount;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}


