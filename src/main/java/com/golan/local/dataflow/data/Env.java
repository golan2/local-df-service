package com.golan.local.dataflow.data;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@ToString
@Data
public class Env {
    private final String org;
    private final String proj;
    private final String env;
    private final UUID uuid;
}
