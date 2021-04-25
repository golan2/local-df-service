package com.golan.local.dataflow.json.registry;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
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
    private String lastStreamUpdate;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("description")
    private String description;
    @JsonProperty("class")
    private String className;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    @JsonIgnore
    public Date getCreatedAtAsDate() throws ParseException {
        if (getCreatedAt() == null) return null;
        return SIMPLE_DATE_FORMAT.parse(getCreatedAt());
    }

    @JsonIgnore
    public Date getLastStreamUpdateAsDate() throws ParseException {
        if (getLastStreamUpdate() == null) return null;
        return SIMPLE_DATE_FORMAT.parse(getLastStreamUpdate());
    }
}


