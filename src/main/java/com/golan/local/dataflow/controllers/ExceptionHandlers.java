package com.golan.local.dataflow.controllers;

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

    @ExceptionHandler(value = { IllegalArgumentException.class })
    protected ResponseEntity<Object> handle(IllegalArgumentException ex, WebRequest request) {
        String bodyOfResponse = "Can't find entity. " + ex.getMessage();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { RejectException.class })
    protected ResponseEntity<Object> handle(RejectException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getResponse(), new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        if (log.isDebugEnabled()) log.debug("Returning: " + status, ex);
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

}
