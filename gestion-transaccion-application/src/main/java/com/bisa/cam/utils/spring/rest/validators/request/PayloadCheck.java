package com.bisa.cam.utils.spring.rest.validators.request;

import com.bisa.cam.utils.spring.rest.validators.request.json.JsonSchema;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

@Target({ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface PayloadCheck {
    JsonSchema jsonSchema() default @JsonSchema(path = "");
}
