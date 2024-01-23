package com.bisa.cam.utils.spring.rest.validators.request;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Collection;

public interface RequestEntityValidator {
    /**
     * @param validatable        the item to validate
     * @param httpServletRequest
     * @return a collection of errors found during validation
     * @throws InvalidRequestParamException
     */
    Collection<Error> matches(Object validatable, HttpServletRequest httpServletRequest) throws InvalidRequestParamException;
}
