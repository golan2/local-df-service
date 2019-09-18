package com.golan.hello.spring.boot.app;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
class OrgProjEnvClass {
    private final String org;
    private final String proj;
    private final String env;
    private final String clazz;
}
