package com.golan.local.dataflow.data;

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
