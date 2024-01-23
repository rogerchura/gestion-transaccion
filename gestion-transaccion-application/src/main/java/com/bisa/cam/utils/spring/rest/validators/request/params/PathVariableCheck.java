package com.bisa.cam.utils.spring.rest.validators.request.params;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;


@Target({ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Qualifier
//@Repeatable(ApiResponses.class)
public @interface PathVariableCheck {
    /**
     * Name of te path variable to chec against
     *
     * @return
     */
    String name() default "";

    @AliasFor("name") String value() default "";

    /**
     * The value in PathVariable must match this regularExpression
     * @return
     */
    String regularExpression() default "";

    /**
     * The value must be one of the elements in this list
     * @return
     */
    String[] allowedValues() default {};
}
