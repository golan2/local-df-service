
package com.golan.hello.spring.orchestration;

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
    private Object cursor;
    @JsonProperty("next_cursor")
    private Object nextCursor;
    @JsonProperty("limit")
    private Integer limit;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("cursor")
    public Object getCursor() {
        return cursor;
    }

    @JsonProperty("cursor")
    public void setCursor(Object cursor) {
        this.cursor = cursor;
    }

    @JsonProperty("next_cursor")
    public Object getNextCursor() {
        return nextCursor;
    }

    @JsonProperty("next_cursor")
    public void setNextCursor(Object nextCursor) {
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
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
