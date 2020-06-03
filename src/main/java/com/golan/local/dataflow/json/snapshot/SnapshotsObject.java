package com.golan.local.dataflow.json.snapshot;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SnapshotsObject {
    @JsonProperty("class_ident")
    private final UUID classUuid;
    @JsonProperty("project")
    private final UUID envUuid;
    @JsonProperty("uuid")
    private final UUID objectUuid;
    @JsonProperty("timestamp")
    private final OffsetDateTime timestamp;


    @JsonCreator
    public SnapshotsObject(@JsonProperty("class_ident") String strClassUuid,
                           @JsonProperty("project") String strEnvUuid,
                           @JsonProperty("uuid")String strObjectUuid,
                           @JsonProperty("timestamp") String strTimestamp) {
        this.classUuid = UUID.fromString(strClassUuid);
        this.envUuid = UUID.fromString(strEnvUuid);
        this.objectUuid = UUID.fromString(strObjectUuid);
        this.timestamp = OffsetDateTime.parse(strTimestamp);
    }

    @Override
    public String toString() {
        return "SnapshotsObject{" +
                "classUuid=" + classUuid +
                ", envUuid=" + envUuid +
                ", objectUuid=" + objectUuid +
                ", timestamp=" + timestamp +
                '}';
    }
}