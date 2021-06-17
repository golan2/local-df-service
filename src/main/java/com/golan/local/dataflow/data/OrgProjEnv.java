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

    public OrgProjEnv(String organization, String projEnv) {
        this.org = organization;

        final String[] split = projEnv.trim().split("~");
        if (split.length == 2) {
            this.project = split[0].trim();
            if (split[1].trim().isEmpty()) {
                this.env = "prod";
            }
            else {
                this.env = split[1].trim();
            }
        }
        else if (split.length == 1) {
            this.project = split[0].trim();
            this.env = "prod";
        }
        else {
            throw new IllegalArgumentException("Can't split ProjEnv: " + projEnv);
        }
    }


    public OrgProjEnv(Env env) {
        this(env.getOrg(), env.getProj(), env.getEnv());
    }

    public OrgProjEnv(OrgProj op) {
        this(op.getOrg(), op.getProj());
    }

    public OrgProjEnv(String envUuid) {
        this("_", "~" + envUuid);
    }
}
