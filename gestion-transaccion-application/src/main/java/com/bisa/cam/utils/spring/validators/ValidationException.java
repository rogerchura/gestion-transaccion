package com.bisa.cam.utils.spring.validators;


import com.bisa.cam.utils.spring.Error;


public class ValidationException extends Exception {
    private final Error error;

    public ValidationException(Error error) {
        super(error.getMessage());
        this.error = error;
    }

    public Error getError() {
        return error;
    }
}
