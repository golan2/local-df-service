package com.golan.hello.spring.boot.app;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
class OrgProj {
    private final String org;
    private final String proj;
}
