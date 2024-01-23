package com.bisa.cam.utils.spring.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorResponseEntity extends ResponseEntity<ErrorResponse> {

    public ErrorResponseEntity(ErrorResponse errorResponse, HttpStatus status) {
        super(errorResponse, status);
    }
}
