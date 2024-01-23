package com.bisa.cam.utils.spring.rest.validators.request.headers;

import com.bisa.cam.utils.spring.Error;
import com.bisa.cam.utils.spring.rest.interceptors.HandlerInterceptorAdapter;
import com.bisa.cam.utils.spring.rest.interceptors.HandlerMethodInterceptor;
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

import java.lang.reflect.Method;
import java.util.*;

@Component
public class RequestHeaderInterceptor implements InterceptorFilter {

    @Autowired
    private RequestInterceptorProperties interceptorProperties;

    @Override
    public Set<String> getHandlerInterceptorPaths() {
        return Collections.singleton(interceptorProperties.requestHeadersInterceptionPath());
    }

    @Override
    public HandlerInterceptorAdapter getHandlerInterceptor() {
        return new HandlerMethodInterceptor() {
            @Override
            protected void preHandle(HttpServletRequest request, HttpServletResponse response,
                                     HandlerMethod handlerMethod) {

                Method method = handlerMethod.getMethod();
                RequestValidation requestValidation = AnnotationUtils.findAnnotation(method, RequestValidation.class);

                if (requestValidation != null && requestValidation.headers().length > 0) {
                    HeaderCheck[] headerChecks = requestValidation.headers();

                    //specific logic starts here

                    final Collection<Error> errors = new LinkedList<>();
                    for (HeaderCheck check : headerChecks) {

                        String header = request.getHeader(check.name());

                        List<Validator> validators = fetchHeaderValidators(check);

                        for (Validator validator : validators) {
                            try {
                                validator.validate(header);
                            } catch (ValidationException x) {
                                errors.add(x.getError());
                            }
                        }
                    }

                    //check if the errors collection has at least one item
                    if (!errors.isEmpty()) {
                        throw new InvalidRequestParamException(errors, request);
                    }
                }
            }
        };
    }

    private List<Validator> fetchHeaderValidators(HeaderCheck headerCheck) {
        List<Validator> validators = new LinkedList<>();

        // numbers: check numeric features

        // strings: check regular expression
        if (StringUtils.isNotBlank(headerCheck.regularExpression())) {
            validators.add(new RegexValidator(new ValidationPredicate<>(headerCheck.name(),
                    headerCheck.regularExpression().trim())));
        } else if (headerCheck.allowedValues().length > 0) {
            validators.add(new ValueSetValidator<>(new ValidationPredicate<>(headerCheck.name(),
                    Arrays.asList(headerCheck.allowedValues()))));
        }

        return validators;
    }
}
