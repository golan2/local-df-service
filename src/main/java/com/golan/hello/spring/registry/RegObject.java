package com.golan.hello.spring.registry;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "identifier",
        "has_cert",
        "lastStreamUpdate",
        "created_at",
        "description",
        "class"
})
public class RegObject {
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("has_cert")
    private Boolean hasCert;
    @JsonProperty("lastStreamUpdate")
    private java.lang.Object lastStreamUpdate;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("description")
    private java.lang.Object description;
    @JsonProperty("class")
    private String _class;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    @JsonProperty("identifier")
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @JsonProperty("has_cert")
    public Boolean getHasCert() {
        return hasCert;
    }

    @JsonProperty("has_cert")
    public void setHasCert(Boolean hasCert) {
        this.hasCert = hasCert;
    }

    @JsonProperty("lastStreamUpdate")
    public java.lang.Object getLastStreamUpdate() {
        return lastStreamUpdate;
    }

    @JsonProperty("lastStreamUpdate")
    public void setLastStreamUpdate(java.lang.Object lastStreamUpdate) {
        this.lastStreamUpdate = lastStreamUpdate;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("description")
    public java.lang.Object getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(java.lang.Object description) {
        this.description = description;
    }

    @JsonProperty("class")
    public String getClass_() {
        return _class;
    }

    @JsonProperty("class")
    public void setClass_(String _class) {
        this._class = _class;
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
