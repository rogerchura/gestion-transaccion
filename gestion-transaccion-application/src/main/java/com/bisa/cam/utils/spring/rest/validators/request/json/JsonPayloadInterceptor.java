package com.bisa.cam.utils.spring.rest.validators.request.json;

import com.bisa.cam.utils.lang.Strings;
import com.bisa.cam.utils.spring.rest.interceptors.HandlerInterceptorAdapter;
import com.bisa.cam.utils.spring.rest.interceptors.InterceptorFilter;
import com.bisa.cam.utils.spring.rest.interceptors.RequestInterceptorProperties;
import com.bisa.cam.utils.spring.rest.validators.request.PayloadCheck;
import com.bisa.cam.utils.spring.rest.validators.request.RequestValidation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class JsonPayloadInterceptor implements InterceptorFilter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @Autowired
    private RequestInterceptorProperties interceptorProperties;

    private final Map<String, com.networknt.schema.JsonSchema> schemaCache = new ConcurrentHashMap<>();

    @Override
    public Set<String> getHandlerInterceptorPaths() {
        return Collections.singleton(interceptorProperties.jsonPayloadInterceptionPath());
    }

    @Override
    public HandlerInterceptorAdapter getHandlerInterceptor() {
        return new HandlerInterceptorAdapter() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                if (handler instanceof HandlerMethod handlerMethod) {
                    Method method = handlerMethod.getMethod();
                    //check if method is annotated with JSONSchema
                    RequestValidation validation = AnnotationUtils.findAnnotation(method, RequestValidation.class);
                    if (validation != null) {
                        PayloadCheck payloadCheck = validation.payload();
                        JsonSchema jsonSchema = payloadCheck.jsonSchema();
                        String jsonSchemaPath = StringUtils.trimToNull(jsonSchema.path());

                        if (jsonSchemaPath != null) {

                            // get JsonSchema from schemaPath
                            com.networknt.schema.JsonSchema schema = getJsonSchema(jsonSchemaPath);

                            // parse json payload
                            JsonNode json = objectMapper.readTree(getJsonPayload(request));

                            // Do actual validation
                            Set<ValidationMessage> validationResult = schema.validate(json);

                            if (!validationResult.isEmpty()) {
                                // throw exception if validation failed
                                throw new JsonValidationException(validationResult, request);
                            }
                        }
                    }
                }
                return super.preHandle(request, response, handler);
            }
        };
    }

    private String getJsonPayload(HttpServletRequest httpServletRequest) throws IOException {
        return StreamUtils.copyToString(httpServletRequest.getInputStream(), StandardCharsets.UTF_8);
    }

    private com.networknt.schema.JsonSchema getJsonSchema(String schemaPath) {
        return schemaCache.computeIfAbsent(schemaPath, path -> {
            Resource resource = resourcePatternResolver.getResource(path);
            if (!resource.exists()) {
                throw new JsonSchemaReadingException(Strings.
                        arrange("Json Schema file '{}' was not found: ", path));
            }
            JsonSchemaFactory schemaFactory = JsonSchemaFactory.
                    getInstance(
                            SpecVersion.
                                    VersionFlag.
                                    valueOf(interceptorProperties.jsonPayloadSchemaSpecVersionVersionFlaq())
                    );
            try (InputStream schemaStream = resource.getInputStream()) {
                return schemaFactory.getSchema(schemaStream);
            } catch (Exception e) {
                throw new JsonSchemaReadingException("An error occurred while loading JSON Schema, path: " + path, e);
            }
        });
    }
}
