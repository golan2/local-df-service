package com.golan.hello.spring.snapshot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SnapshotsSearchRespBody {

    @JsonProperty("objects")
    private final List<SnapshotsObject> objects;
    @JsonProperty("meta")
    private final DataflowMeta meta;


    @JsonCreator
    public SnapshotsSearchRespBody(@JsonProperty("objects") List<SnapshotsObject> objects,
                                   @JsonProperty("meta") DataflowMeta meta) {
        this.objects = objects;
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "SnapshotsSearchRespBody{" +
                "objects=" + objects +
                ", meta=" + meta +
                '}';
    }
}