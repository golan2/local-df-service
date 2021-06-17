package com.golan.local.dataflow.controllers;

import com.golan.local.dataflow.authentication.InternalApiUnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@SuppressWarnings("unused")
@Slf4j
@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handle(Exception ex, WebRequest request) {
        final String bodyOfResponse = "INTERNAL-SERVER-ERROR " + ex.getMessage();
        log.error(bodyOfResponse, ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handle(IllegalArgumentException ex, WebRequest request) {
        String bodyOfResponse = "IllegalArgumentException " + ex.getMessage();
        log.error(bodyOfResponse, ex);
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { RejectException.class })
    protected ResponseEntity<Object> handle(RejectException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getResponse(), new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @ExceptionHandler(value = { InternalApiUnauthorizedException.class })
    protected ResponseEntity<Object> handle(InternalApiUnauthorizedException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getResponse(), new HttpHeaders(), ex.getHttpStatus(), request);
    }
}
