package com.bisa.cam.utils.spring.rest.validators.request.json;

import com.bisa.cam.utils.spring.Error;
import com.bisa.cam.utils.spring.rest.ErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JsonValidationExceptionHandler {

    @ExceptionHandler(JsonValidationException.class)
    public ResponseEntity<Map<String, Object>> onJsonValidationFailedException(JsonValidationException ex) {
        List<Error> errors = ex.getValidationMessages().stream()
                .map(vex -> new Error(null, vex.getMessage(),
                        vex.getPath()))
                .collect(Collectors.toList());

        return ErrorResponse.badRequest()
                .withErrors(errors)
                .withPath(ex.getRequestURI())
                .build()
                .toResponseEntity();
    }
}
