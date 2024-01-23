package com.bisa.cam.utils.spring.rest.interceptors;

import jakarta.servlet.*;

import java.io.IOException;
import java.util.Set;

/**
 * A generic Http filter implementation ready to handle the request and response in an intermediate level
 * <br/>
 * These implementations are configured by the {@link InterceptorsConfiguration} class, so there's no need to provide any additional setup
 *
 */
public interface InterceptorFilter extends Filter {
    /**
     * Returns a list of classes to be affected by this filter
     *
     * @return
     */
    Set<String> getHandlerInterceptorPaths();

    /**
     * This will provide a helpful method to bind the filter with a specific logic
     *
     * @return
     */
    HandlerInterceptorAdapter getHandlerInterceptor();

    /**
     * By default, will chain the other filters,when needed, this method can be overridden with specific logic
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    default void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
    }
}
