package com.golan.local.dataflow.controllers;

import com.golan.local.dataflow.authentication.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * The error usually looks like:
 *      {
 *          "message":"<< here goes a short message that tells what the problem is>>",
 *          "description": "<<Here goes some long message that is always the same and refers you to the documentation>>"
 *      }
 * So we need only the short message because the long one (description) is a constant.
 */
@Getter
public class RejectException extends RuntimeException {
    private static final String ERR_DESC = "The resource you requested could not be found. Please verify that the correct resource path was provided and that the provided access token is authorized to access it, or refer to https://att-dataflow.api-docs.io for complete API documentation.";

    private final HttpStatus httpStatus;
    private final ErrorResponse response;

    public RejectException(HttpStatus httpStatus) {
        this(httpStatus, httpStatus.getReasonPhrase());
    }

    RejectException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.response = new ErrorResponse(message, ERR_DESC);
    }

    @Override
    public String getMessage() {
        return httpStatus.value() + " | " + response.getMessage() + " | " + response.getDescription() + " | " + getError();
    }

    private String getError() {
        if (response.getErrors() == null)
            return "null";
        else
            return response.getErrors().getError();
    }

}
