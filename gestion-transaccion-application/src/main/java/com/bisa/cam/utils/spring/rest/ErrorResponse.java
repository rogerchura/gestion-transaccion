package com.bisa.cam.utils.spring.rest;

import com.bisa.cam.utils.spring.Error;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder({"status", "error", "errors", "message", "path", "timestamp"})
public class ErrorResponse<T> implements Serializable {
    final Integer status;
    final String error;
    final Collection<Error> errors;
    final String message;
    final String path;

    final Long timestamp;

    public ErrorResponse(@JsonProperty("status") int status, @JsonProperty("error") String error,
                         @JsonProperty("errors") Collection<Error> errors, @JsonProperty("message") String message,
                         @JsonProperty("path") String path, @JsonProperty("timestamp") Long timestamp) {
        this.status = status;
        this.error = error;
        this.errors = errors;
        this.message = message;
        this.path = path;
        this.timestamp = timestamp;
    }

    public static ErrorResponseBuilder internalServerError() {
        return of(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public static ErrorResponse internalServerError(Exception x) {
        return responseErrorOfException(HttpStatus.INTERNAL_SERVER_ERROR, x);
    }

    public static ErrorResponse internalServerError(Throwable x) {
        return of(HttpStatus.INTERNAL_SERVER_ERROR).withErrors(Collections.singleton(Error.of(x))).build();
    }

    public static ErrorResponseBuilder badRequest() {
        return of(HttpStatus.BAD_REQUEST);
    }

    public static ErrorResponse badRequest(Exception x) {
        return responseErrorOfException(HttpStatus.BAD_REQUEST, x);
    }

    public static ErrorResponseBuilder unauthorized() {
        return of(HttpStatus.UNAUTHORIZED);
    }

    public static ErrorResponse unauthorized(Exception x) {
        return responseErrorOfException(HttpStatus.UNAUTHORIZED, x);
    }

    public static ErrorResponseBuilder forbidden() {
        return of(HttpStatus.FORBIDDEN);
    }

    public static ErrorResponse forbidden(Exception x) {
        return responseErrorOfException(HttpStatus.FORBIDDEN, x);
    }

    public static ErrorResponseBuilder notFound() {
        return of(HttpStatus.NOT_FOUND);
    }

    public static ErrorResponse notFound(Exception x) {
        return responseErrorOfException(HttpStatus.NOT_FOUND, x);
    }

    public static ErrorResponseBuilder notModified() {
        return of(HttpStatus.NOT_MODIFIED);
    }

    public static ErrorResponse notModified(Exception x) {
        return responseErrorOfException(HttpStatus.NOT_MODIFIED, x);
    }

    public static ErrorResponseBuilder gone() {
        return of(HttpStatus.GONE);
    }

    public static ErrorResponse gone(Exception x) {
        return responseErrorOfException(HttpStatus.GONE, x);
    }

    public static ErrorResponseBuilder notAcceptable() {
        return of(HttpStatus.NOT_ACCEPTABLE);
    }

    public static ErrorResponse notAcceptable(Exception x) {
        return responseErrorOfException(HttpStatus.NOT_ACCEPTABLE, x);
    }

    public static ErrorResponseBuilder unsupportedMediaType() {
        return of(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    public static ErrorResponseBuilder unprocessableEntity() {
        return of(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    public static ErrorResponse unprocessableEntity(Exception x) {
        return responseErrorOfException(HttpStatus.UNPROCESSABLE_ENTITY, x);
    }

    public static ErrorResponseBuilder builderOfunprocessableEntity(Exception x) {
        return responseBuilderErrorOfException(HttpStatus.UNPROCESSABLE_ENTITY, x);
    }

    public static ErrorResponseBuilder preconditionFailed() {
        return of(HttpStatus.PRECONDITION_FAILED);
    }

    public static ErrorResponse preconditionFailed(Exception x) {
        return responseErrorOfException(HttpStatus.PRECONDITION_FAILED, x);
    }

    public static ErrorResponseBuilder preconditionRequired() {
        return of(HttpStatus.PRECONDITION_REQUIRED);
    }

    public static ErrorResponse preconditionRequired(Exception x) {
        return responseErrorOfException(HttpStatus.PRECONDITION_REQUIRED, x);
    }

    public static ErrorResponseBuilder tooManyRequests() {
        return of(HttpStatus.TOO_MANY_REQUESTS);
    }

    public static ErrorResponse tooManyRequests(Exception x) {
        return responseErrorOfException(HttpStatus.TOO_MANY_REQUESTS, x);
    }

    public static ErrorResponseBuilder conflict() {
        return of(HttpStatus.CONFLICT);
    }

    public static ErrorResponse conflict(Exception x) {
        return responseErrorOfException(HttpStatus.CONFLICT, x);
    }

    public static ErrorResponseBuilder expectationFailed() {
        return of(HttpStatus.EXPECTATION_FAILED);
    }

    public static ErrorResponse expectationFailed(Exception x) {
        return responseErrorOfException(HttpStatus.EXPECTATION_FAILED, x);
    }

    public static ErrorResponseBuilder of(HttpStatus httpStatus) {
        return new ErrorResponseBuilder().withHttpStatus(httpStatus).withMessage(httpStatus.getReasonPhrase());
    }

    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder();
    }

    public static ErrorResponseBuilder builder(HttpServletRequest httpServletRequest) {
        return new ErrorResponseBuilder().withPath(httpServletRequest.getRequestURI());
    }

    private static ErrorResponse responseErrorOfException(HttpStatus httpStatus, Exception x) {
        return of(httpStatus).withErrors(Collections.singleton(Error.of(x))).build();
    }

    private static ErrorResponseBuilder responseBuilderErrorOfException(HttpStatus httpStatus, Exception x) {
        return of(httpStatus).withErrors(Collections.singleton(Error.of(x)));
    }

    public ResponseEntity toResponseEntity() {
        return new ResponseEntity(this, getHttpStatus());
    }

    @JsonIgnore
    public HttpStatus getHttpStatus() {
        return HttpStatus.valueOf(status);
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Collection<Error> getErrors() {
        return errors;
    }

    public static final class ErrorResponseBuilder {
        private HttpStatus httpStatus;
        private String message;
        private String error;
        private String path;
        private Instant timestamp = Instant.now();
        private Collection<Error> errors;

        private ErrorResponseBuilder() {
        }

        public ErrorResponseBuilder withHttpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public ErrorResponseBuilder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponseBuilder withPath(String path) {
            this.path = path;
            return this;
        }

        public ErrorResponseBuilder withTimestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorResponseBuilder withErrors(Collection<Error> errors) {
            this.errors = errors;
            return this;
        }

        public ErrorResponseBuilder withError(Error error) {
            this.errors = Collections.singleton(error);
            return this;
        }

        public ErrorResponseBuilder error(String error) {
            this.error = error;
            return this;
        }

        public <E extends ErrorResponse<?>> E build() {
            //instant to zone
            return (E) toUnixTimeMillis();
        }

        /*public <E extends ErrorResponse<LocalDateTime>> E toISO8601(boolean useUTC) {
            if (timestamp != null)
                return (E) new ErrorResponse(
                        this.httpStatus.value(), error, errors, message, path,
                        useUTC ? Temporals.toUTCDateTime(timestamp.toEpochMilli()) : Temporals.toLocalDateTime(timestamp.toEpochMilli()));
            return (E) new ErrorResponse(this.httpStatus.value(), error, errors, message, path, (LocalDateTime) null);
        }*/

        public <E extends ErrorResponse<Long>> E toUnixTimeMillis() {
            if (timestamp != null)
                return (E) new ErrorResponse(this.httpStatus.value(),
                        Optional.ofNullable(error).orElse(httpStatus.getReasonPhrase()),
                        errors, message, path, timestamp.toEpochMilli());
            return (E) new ErrorResponse(this.httpStatus.value(),
                    Optional.ofNullable(error).orElse(httpStatus.getReasonPhrase()),
                    errors, message, path, null);
        }
    }
}
