package com.bisa.cam.utils.spring.rest.validators.request.params;

import com.bisa.cam.utils.spring.Error;
import com.bisa.cam.utils.spring.rest.interceptors.HandlerInterceptorAdapter;
import com.bisa.cam.utils.spring.rest.interceptors.InterceptorFilter;
import com.bisa.cam.utils.spring.rest.interceptors.RequestInterceptorProperties;
import com.bisa.cam.utils.spring.rest.validators.request.InvalidRequestParamException;
import com.bisa.cam.utils.spring.rest.validators.request.RequestValidation;
import com.bisa.cam.utils.spring.validators.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;

import java.lang.reflect.Method;
import java.util.*;


@Component
public class PathVariableInterceptor implements InterceptorFilter {

    @Autowired
    private RequestInterceptorProperties interceptorProperties;

    @Override
    public Set<String> getHandlerInterceptorPaths() {
        return Collections.singleton(interceptorProperties.pathVariablesInterceptionPath());
    }

    @Override
    public HandlerInterceptorAdapter getHandlerInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

                if (handler instanceof HandlerMethod handlerMethod) {
                    Map<String, Object> pathVariables = (Map<String, Object>) request.
                            getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

                    Method method = handlerMethod.getMethod();
                    //check if method is annotated with JSONSchema
                    RequestValidation requestValidation = AnnotationUtils.
                            findAnnotation(method, RequestValidation.class);
                    if (requestValidation != null && !pathVariables.isEmpty()) {

                        final Collection<Error> errorsFound = new LinkedList<>();

                        //fetch all PathVariableCheck configured
                        PathVariableCheck[] checks = requestValidation.pathVariables();

                        for (PathVariableCheck check : checks) {
                            Object object = pathVariables.get(check.name());

                            List<Validator> validators = fetchPathVariableValidators(check);
                            for (Validator validator : validators) {
                                try {
                                    validator.validate(object);
                                } catch (ValidationException x) {
                                    errorsFound.add(x.getError());
                                }
                            }

                        }

                        //check if the errors collection has at least one item
                        if (!errorsFound.isEmpty()) {
                            throw new InvalidRequestParamException(errorsFound, request);
                        }
                    }
                }

                return super.preHandle(request, response, handler);
            }
        };
    }

    private List<Validator> fetchPathVariableValidators(PathVariableCheck pathVariableCheck) {
        List<Validator> validators = new LinkedList<>();

        // numbers: check numeric features

        // strings: check regular expression
        if (StringUtils.isNotBlank(pathVariableCheck.regularExpression())) {
            validators.add(new RegexValidator(new ValidationPredicate<>(pathVariableCheck.name(),
                    pathVariableCheck.regularExpression().trim())));
        } else if (pathVariableCheck.allowedValues().length > 0) {
            validators.add(new ValueSetValidator<>(new ValidationPredicate<>(pathVariableCheck.name(),
                    Arrays.asList(pathVariableCheck.allowedValues()))));
        }

        return validators;
    }
}
