package com.bisa.cam;

import org.springframework.boot.Banner;
import org.springframework.boot.ResourceBanner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.io.ClassPathResource;

/**
 * By using IntellijIdea, you can place the following configuration in header file:
 *
 * @author ${FULL_NAME}
 * @version 1.0: ${NAME}.java; ${MONTH_NAME_FULL}. ${DAY}, ${YEAR} @ ${TIME}
 */
@SpringBootApplication(scanBasePackages = {"com.bisa", "com.bisa.cam"})
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder().
                bannerMode(Banner.Mode.LOG).
                banner(new ResourceBanner(new ClassPathResource("spring/banner.txt"))).
                sources(Application.class).
                run(args);
    }
}