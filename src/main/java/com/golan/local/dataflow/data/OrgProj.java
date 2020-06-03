package com.golan.local.dataflow.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@Data
public class OrgProj {
    private final String org;
    private final String proj;
}
