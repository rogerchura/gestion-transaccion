package com.bisa.cam.utils.spring.io;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>This is a JEE filter that helps with the interception at early stage in the processing of request/response to allow changing the main objects involved in this.</p>
 * <p>{@link HttpServletRequest} and {@link HttpServletResponse}, will be wrapped in cached content instances
 * <p>This is the only one component that uses a native instance to handle life cycle values</p>
 *
 */
@Component
public class CachedRequestResponseFilter implements Filter {

    final Logger logger = LogManager.getLogger(getClass());

    final Set<String> requestPayloadAvailablePaths = new LinkedHashSet<>();

    @Autowired
    public CachedRequestResponseFilter(ApplicationContext applicationContext) {
        logger.debug("* * * * * * * * * * * * * * * Initializing filter * * * * * * * * * * * * * * * * * * *");

        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(Controller.class);
        Iterator<Map.Entry<String, Object>> iterator = beans.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            Object value = next.getValue();
            Class<?> beanClass = value.getClass();
            Annotation annotation = AnnotationUtils.findAnnotation(beanClass, EnableCachingRequestResponse.class);
            if (annotation != null) {
                RequestMapping mapping = AnnotationUtils.findAnnotation(beanClass, RequestMapping.class);
                if (mapping == null) continue;

                if (mapping.path().length > 0) {
                    requestPayloadAvailablePaths.addAll(Arrays.asList(mapping.path()));
                } else {
                    //the paths are defined in methods, need to fetch paths from each method
                    Method[] methods = beanClass.getMethods();
                    for (Method method : methods) {
                        String[] path = findBestAnnotation(method);
                        if (path != null && path.length > 0) {
                            requestPayloadAvailablePaths.addAll(Arrays.asList(path).stream().map(s -> s.contains("{") ? s.substring(0, s.indexOf("{")) : s).collect(Collectors.toList()));
                        }
                    }
                }
            }
        }

        logger.info("Cacheable payloads enabled for following paths:[{}]",
                requestPayloadAvailablePaths.stream().collect(Collectors.joining(",")));
    }

    private String[] findBestAnnotation(Method method) {
        PostMapping pm = AnnotationUtils.findAnnotation(method, PostMapping.class);
        if (pm != null)
            return pm.path();

        GetMapping gm = AnnotationUtils.findAnnotation(method, GetMapping.class);
        if (gm != null)
            return gm.path();

        DeleteMapping dm = AnnotationUtils.findAnnotation(method, DeleteMapping.class);
        if (dm != null)
            return dm.path();

        PutMapping pum = AnnotationUtils.findAnnotation(method, PutMapping.class);
        if (pum != null)
            return pum.path();

        RequestMapping rm = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        if (rm != null) return rm.path();

        return null;
    }

    public Set<String> getRequestPayloadAvailablePaths() {
        return requestPayloadAvailablePaths;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //retrieve all endpoints that require requests to be wrapped
    }

    @Override
    public void destroy() {
        //lazy by now
        logger.debug("* * * * * * * * * * * * * * * Destroying filter * * * * * * * * * * * * * * * * * * *");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
            IOException, ServletException {

        Instant begin = Instant.now();

        String requestURI = ((HttpServletRequest) request).getRequestURI();

        if (!requestPayloadAvailablePaths.stream().filter(p -> requestURI.startsWith(p)).findFirst().isPresent()) {
            chain.doFilter(request, response);
            return;
        }

//        logger.debug("Attempting to build a cached pair of request and response for path [{}]", ((HttpServletRequest) request).getRequestURI());
        CachedResponseWrapper cachedResponse = new CachedResponseWrapper((HttpServletResponse) response);
        CachedRequestWrapper cachedRequest = new CachedRequestWrapper((HttpServletRequest) request);
        chain.doFilter(cachedRequest, cachedResponse);
        cachedResponse.copyBodyToResponse();

        //logger.debug("Request processed in: {}", Temporals.explainDuration(begin, Instant.now()));
    }
}