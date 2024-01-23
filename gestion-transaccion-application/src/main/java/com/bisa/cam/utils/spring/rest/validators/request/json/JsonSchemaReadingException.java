package com.bisa.cam.utils.spring.rest.validators.request.json;

public class JsonSchemaReadingException extends RuntimeException {

    public JsonSchemaReadingException(String message) {
        super(message);
    }

    public JsonSchemaReadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
