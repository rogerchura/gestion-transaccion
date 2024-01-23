package com.bisa.cam.utils.spring.rest.validators.request.json;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;


@Target({ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface JsonSchema {
    /**
     * The full path to Json Schema for validating payload
     * @return
     */
    @AliasFor("value") String path() default "";

    @AliasFor("path") String value() default "";
}
