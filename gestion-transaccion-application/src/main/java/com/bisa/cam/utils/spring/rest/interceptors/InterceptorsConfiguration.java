package com.bisa.cam.utils.spring.rest.interceptors;

//import com.bisa.commons.lang.Annotations;
import com.bisa.cam.utils.lang.Annotations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Configuration
@EnableConfigurationProperties(RequestInterceptorProperties.class)
public class InterceptorsConfiguration implements WebMvcConfigurer {

    final Logger logger = LogManager.getLogger(getClass());

    private final ApplicationContext applicationContext;

    @Autowired
    public InterceptorsConfiguration(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        Map<String, InterceptorFilter> filters = applicationContext.getBeansOfType(InterceptorFilter.class);
        logger.info("{} instances of InterceptorFilter have been found", filters.size());

        for (String keyFilter : filters.keySet()) {
            InterceptorFilter interceptorFilter = filters.get(keyFilter);
            prepare(interceptorFilter, interceptorRegistry);
        }

        //lookup classes that extends HandlerInterceptorAdapter
        Map<String, HandlerInterceptor> hias = applicationContext.getBeansOfType(HandlerInterceptor.class);
        logger.info("{} instances of HandlerInterceptorAdapter have been found", hias.size());

        for (String key : hias.keySet()) {
            HandlerInterceptor handlerInterceptorAdapter = hias.get(key);
            prepare(handlerInterceptorAdapter, interceptorRegistry);
        }
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
//        resolvers.add(new UserInfoHandlerMethodArgumentResolver());
    }

    private void prepare(InterceptorFilter interceptorFilter, InterceptorRegistry interceptorRegistry) {

        Set<String> paths = interceptorFilter.getHandlerInterceptorPaths();

        paths.stream().forEach(path -> interceptorRegistry
                .addInterceptor(interceptorFilter.getHandlerInterceptor())
                .addPathPatterns(path + (path.endsWith("/") ? "**" : "/**"))
        );

        logger.info("- {} interceptor filter bound to paths [{}] successfully", interceptorFilter.getClass(), interceptorFilter.getHandlerInterceptorPaths());
    }

    private void prepare(HandlerInterceptor handlerInterceptorAdapter, InterceptorRegistry interceptorRegistry) {

        //Set<String> paths = interceptorFilter.getHandlerInterceptorPaths();

        //TODO, this is applying to all requests, this generates that Cache works for some, and Tracing excpects for all
        String path = "/";

        Optional<Order> order = Annotations.findAnnotationInType(handlerInterceptorAdapter.getClass(), Order.class);

        if (order.isPresent()) {
            interceptorRegistry
                    .addInterceptor(handlerInterceptorAdapter)
                    .order(order.get().value())
                    .addPathPatterns(path + (path.endsWith("/") ? "**" : "/**"));
        } else {
            interceptorRegistry
                    .addInterceptor(handlerInterceptorAdapter)
                    .addPathPatterns(path + (path.endsWith("/") ? "**" : "/**"));
        }

        logger.info("- {} handler interceptor adapter bound to paths [{}] successfully", handlerInterceptorAdapter.getClass(), path);
    }
}
