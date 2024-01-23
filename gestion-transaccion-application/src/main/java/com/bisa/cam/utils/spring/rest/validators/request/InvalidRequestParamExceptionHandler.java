package com.bisa.cam.utils.spring.rest.validators.request;

import com.bisa.cam.utils.spring.rest.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class InvalidRequestParamExceptionHandler {

    final Logger logger = LogManager.getLogger(getClass());

    @ExceptionHandler(InvalidRequestParamException.class)
    public ResponseEntity<Map<String, Object>> onJsonValidationFailedException(InvalidRequestParamException ex) {

        logger.error("No se puede continuar con la solicitud por un valor inesperado en los parametros: " +
                ex.getMessage());

        return ErrorResponse.badRequest()
                .withMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .withErrors(ex.getFailures())
                .withPath(ex.getRequestURI())
                .build()
                .toResponseEntity();
    }
}
