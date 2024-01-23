package com.bisa.cam.utils.spring.rest.validators.request;

import com.bisa.cam.utils.spring.rest.validators.request.headers.HeaderCheck;
import com.bisa.cam.utils.spring.rest.validators.request.params.PathVariableCheck;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.METHOD;

@Target({METHOD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequestValidation {
    /**
     * Validate {@link org.springframework.web.bind.annotation.PathVariable} in request
     * @return
     */
    PathVariableCheck[] pathVariables() default {};

    /**
     * Validate {@link org.springframework.web.bind.annotation.RequestBody} in request
     * @return
     */
    PayloadCheck payload() default @PayloadCheck;

    HeaderCheck[] headers() default {};
}
