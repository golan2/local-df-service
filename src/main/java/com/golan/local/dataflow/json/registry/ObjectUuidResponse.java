package com.golan.local.dataflow.json.registry;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
//{"class_uuid":"7a3567c7-6494-4e34-bae0-beb1a2a26770","uuid":"d8cd975c-92ff-11eb-8f3b-3df2427f0362"}
public class ObjectUuidResponse {
    @JsonProperty("class_uuid")
    private final String classUuid;

    @JsonProperty("uuid")
    private final String uuid;

}
