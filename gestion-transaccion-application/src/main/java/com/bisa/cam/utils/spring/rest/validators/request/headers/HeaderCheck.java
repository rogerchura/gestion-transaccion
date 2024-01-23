package com.bisa.cam.utils.spring.rest.validators.request.headers;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;


@Target({ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface HeaderCheck {
    /**
     * Name of the header to check against
     *
     * @return
     */
    String name() default "";

    @AliasFor("name") String value() default "";

    /**
     * The value in {@link org.springframework.web.bind.annotation.RequestHeader} this regularExpression must match to
     *
     * @return
     */
    String regularExpression() default "";

    /**
     * The value of the {@link org.springframework.web.bind.annotation.RequestHeader} must be an item of this list
     *
     * @return
     */
    String[] allowedValues() default {};
}