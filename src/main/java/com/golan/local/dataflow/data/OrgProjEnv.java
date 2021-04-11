package com.golan.local.dataflow.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class OrgProjEnv implements Serializable {
    private String org;
    private String project;
    private String env;

    public OrgProjEnv(Env env) {
        this(env.getOrg(), env.getProj(), env.getEnv());
    }
}
