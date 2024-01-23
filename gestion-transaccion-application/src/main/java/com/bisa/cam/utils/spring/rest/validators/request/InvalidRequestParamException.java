package com.bisa.cam.utils.spring.rest.validators.request;

import com.bisa.cam.utils.spring.Error;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;

public class InvalidRequestParamException extends RuntimeException {
    private final String requestURI;

    private final Collection<Error> errors;

    public InvalidRequestParamException(Collection<Error> errors, HttpServletRequest request) {
        this(errors, request.getRequestURI());
    }

    public InvalidRequestParamException(Collection<Error> errors, String requestURI) {
        this.errors = errors;
        this.requestURI = requestURI;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public Collection<Error> getFailures() {
        return errors;
    }
}
