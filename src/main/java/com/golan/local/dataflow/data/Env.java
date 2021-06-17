package com.golan.local.dataflow.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@ToString
@Data
@AllArgsConstructor
public class Env {
    private final String org;
    private final String proj;
    private final String env;
    private final UUID uuid;

    public Env(OrgProjEnv ope) {
        this(ope.getOrg(), ope.getProject(), ope.getEnv(), null);
    }
}
