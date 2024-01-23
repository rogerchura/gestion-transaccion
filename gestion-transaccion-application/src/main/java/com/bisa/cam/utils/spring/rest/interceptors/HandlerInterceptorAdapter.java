package com.bisa.cam.utils.spring.rest.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public abstract class HandlerInterceptorAdapter implements AsyncHandlerInterceptor {

    /**
     * This implementation always returns {@code true}.
     * <p>
     * {@inheritDoc}
     * </p>
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        return true;
    }

    /**
     * This implementation is empty.
     * <br/>
     * {@inheritDoc}
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    /**
     * This implementation is empty.<br/>
     * {@inheritDoc}
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
    }

    /**
     * This implementation is empty.</br>
     * {@inheritDoc}
     */
    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response,
                                               Object handler) {
    }
}
