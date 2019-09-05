package com.golan.hello.spring.registry;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "cursor",
        "next_cursor",
        "limit"
})
public class Meta {

    @JsonProperty("cursor")
    private String cursor;
    @JsonProperty("next_cursor")
    private String nextCursor;
    @JsonProperty("limit")
    private Integer limit;
    @JsonIgnore
    private Map<String, java.lang.Object> additionalProperties = new HashMap<>();

    @JsonProperty("cursor")
    public String getCursor() {
        return cursor;
    }

    @JsonProperty("cursor")
    public void setCursor(String cursor) {
        this.cursor = cursor;
    }

    @JsonProperty("next_cursor")
    public String getNextCursor() {
        return nextCursor;
    }

    @JsonProperty("next_cursor")
    public void setNextCursor(String nextCursor) {
        this.nextCursor = nextCursor;
    }

    @JsonProperty("limit")
    public Integer getLimit() {
        return limit;
    }

    @JsonProperty("limit")
    public void setLimit(Integer limit) {
        this.limit = limit;
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
