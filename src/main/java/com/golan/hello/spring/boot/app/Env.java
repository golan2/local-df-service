package com.golan.hello.spring.boot.app;

import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@ToString
@Data
class Env {
    private final String org;
    private final String proj;
    private final String env;
    private final UUID uuid;
}
