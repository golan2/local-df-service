package com.golan.hello.spring.orchestration.spec;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.UUID;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UuidResponse {
    private final UUID uuid;

    public UuidResponse(@JsonProperty("env_uuid") UUID uuid) {
        this.uuid = uuid;
    }
}
