package com.golan.local.dataflow.controllers;

import com.golan.local.dataflow.data.OrgProjEnv;
import org.springframework.http.HttpStatus;

public class ProjectDoesNotExistsException extends RejectException {
    private final OrgProjEnv ope;

    public ProjectDoesNotExistsException(OrgProjEnv ope) {
        super(HttpStatus.NOT_FOUND, "Project does not exist");
        this.ope = ope;
    }
}
