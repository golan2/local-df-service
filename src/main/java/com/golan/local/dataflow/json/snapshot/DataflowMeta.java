package com.golan.local.dataflow.json.snapshot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DataflowMeta {
    @JsonProperty("limit")
    private final long limit;
    @JsonProperty("cursor")
    private final String cursor;
    @JsonProperty("nextCursor")
    private final String nextCursor;

    @JsonCreator
    public DataflowMeta(@JsonProperty("limit") long limit,
                        @JsonProperty("cursor") String cursor,
                        @JsonProperty("nextCursor") String nextCursor) {
        this.limit = limit;
        this.cursor = cursor;
        this.nextCursor = nextCursor;
    }

}