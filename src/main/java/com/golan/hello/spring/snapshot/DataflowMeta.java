package com.golan.hello.spring.snapshot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

    @Override
    public String toString() {
        return "DataflowMeta{" +
                "limit=" + limit +
                ", cursor=" + cursor +
                ", nextCursor=" + nextCursor +
                '}';
    }
}