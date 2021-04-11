package com.golan.local.dataflow.json.registry;


import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
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
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    @JsonIgnore
    private UUID objectUuid;  //we need it to use it internally but it is not part of the JSON we return on REST API
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("has_cert")
    private Boolean hasCert;
    @JsonProperty("lastStreamUpdate")
    private java.lang.Object lastStreamUpdate;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("description")
    private String description;
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
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
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

    public Date getCreatedAtAsDate() throws ParseException {
        return SIMPLE_DATE_FORMAT.parse(getCreatedAt());
    }
}
