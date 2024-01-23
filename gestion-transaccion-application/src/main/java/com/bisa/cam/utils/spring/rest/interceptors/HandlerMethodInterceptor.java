package com.bisa.cam.utils.spring.rest.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


public abstract class HandlerMethodInterceptor extends HandlerInterceptorAdapter {

    /**
     * This implementation always returns {@code true}.
     * <p>
     * {@inheritDoc}
     * </p>
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            this.preHandle(request, response, (HandlerMethod) handler);
        }

        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            this.postHandle(request, response, (HandlerMethod) handler);
        }
    }

    /**
     * Handler the operations in a more specific way
     *
     * @param request
     * @param response
     * @param handlerMethod
     * @return
     */
    protected abstract void preHandle(HttpServletRequest request, HttpServletResponse response,
                                      HandlerMethod handlerMethod);

    /**
     * Invoked on post handle
     *
     * @param request
     * @param response
     * @param handlerMethod
     */
    protected void postHandle(HttpServletRequest request, HttpServletResponse response,
                              HandlerMethod handlerMethod) {
    }
}
