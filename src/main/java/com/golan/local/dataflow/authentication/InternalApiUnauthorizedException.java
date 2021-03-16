package com.golan.local.dataflow.authentication;

import com.golan.local.dataflow.controllers.RejectException;
import org.springframework.http.HttpStatus;

public class InternalApiUnauthorizedException extends RejectException {
    InternalApiUnauthorizedException() {
        super(HttpStatus.NOT_FOUND);
    }
}
