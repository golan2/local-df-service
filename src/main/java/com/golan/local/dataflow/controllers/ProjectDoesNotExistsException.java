package com.golan.local.dataflow.controllers;

import org.springframework.http.HttpStatus;

public class ProjectDoesNotExistsException extends RejectException {
    public ProjectDoesNotExistsException() {
        super(HttpStatus.NOT_FOUND, "Project does not exist.");
    }
}
