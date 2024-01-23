package com.bisa.cam.utils.spring.io;

import java.lang.annotation.*;

/**
 * Any class that implements this annotation will allow to main filter to handle requests as readable many times
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableCachingRequestResponse {
}
