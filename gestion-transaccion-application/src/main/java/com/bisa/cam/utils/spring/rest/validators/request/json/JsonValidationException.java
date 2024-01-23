package com.bisa.cam.utils.spring.rest.validators.request.json;

import com.networknt.schema.ValidationMessage;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

public class JsonValidationException extends RuntimeException {
    private final Set<ValidationMessage> validationMessages;

    private final String requestURI;

    public JsonValidationException(Set<ValidationMessage> validationMessages, HttpServletRequest httpServletRequest) {
        this(validationMessages, httpServletRequest.getRequestURI());
    }

    public JsonValidationException(Set<ValidationMessage> validationMessages, String requestURI) {
        super("Json Schema validation error");
        this.validationMessages = validationMessages;
        this.requestURI = requestURI;
    }

    public Set<ValidationMessage> getValidationMessages() {
        return validationMessages;
    }

    public String getRequestURI() {
        return requestURI;
    }
}
