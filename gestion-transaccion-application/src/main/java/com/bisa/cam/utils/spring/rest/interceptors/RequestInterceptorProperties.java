package com.bisa.cam.utils.spring.rest.interceptors;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "application.interceptors.request")
public record RequestInterceptorProperties(
        @Name("headers.path") @DefaultValue("/") String requestHeadersInterceptionPath,

        @Name("json-payload.path") @DefaultValue("/") String jsonPayloadInterceptionPath,
        @Name("json-payload.schema.specVersionVersionFlag")
        @DefaultValue("V201909") String jsonPayloadSchemaSpecVersionVersionFlaq,

        @Name("path-variables.path") @DefaultValue("/") String pathVariablesInterceptionPath,

        @Name("tracing.paths") @DefaultValue({"/"}) String[] tracingInterceptionPaths
) {
}