package com.bisa.cam.utils.spring.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface RestControllerStub {

    /**
     * OK (200)
     *
     * @return
     */
    default ResponseEntity<?> ok() {
        return ok(null);
    }

    default <T> ResponseEntity<T> ok(T body) {
        return ok(body, new HttpHeaders());
    }

    default <T> ResponseEntity<T> ok(T body, HttpHeaders headers) {
        return respond(body, HttpStatus.OK, headers);
    }

    /**
     * CREATED (201)
     *
     * @return
     */
    default ResponseEntity<?> created() {
        return created(null);
    }

    default <T> ResponseEntity<T> created(T body) {
        return created(body, new HttpHeaders());
    }

    default <T> ResponseEntity<T> created(T body, HttpHeaders headers) {
        return respond(body, HttpStatus.CREATED, headers);
    }

    /**
     * NO CONTENT (204)
     *
     * @return
     */
    default ResponseEntity<?> noContent() {
        return noContent(null);
    }

    default <T> ResponseEntity<T> noContent(T body) {
        return noContent(body, new HttpHeaders());
    }

    default <T> ResponseEntity<T> noContent(T body, HttpHeaders headers) {
        return respond(body, HttpStatus.NO_CONTENT, headers);
    }

    /**
     * NOT MODIFIED (304)
     *
     * @return
     */
    default ResponseEntity<?> notModified() {
        return notModified(null);
    }

    default <T> ResponseEntity<T> notModified(T body) {
        return notModified(body, new HttpHeaders());
    }

    default <T> ResponseEntity<T> notModified(T body, HttpHeaders headers) {
        return respond(body, HttpStatus.NOT_MODIFIED, headers);
    }

    /**
     * BAD REQUEST (400)
     *
     * @return
     */
    default ResponseEntity<?> badRequest() {
        return badRequest(null);
    }

    default <T> ResponseEntity<T> badRequest(T body) {
        return badRequest(body, new HttpHeaders());
    }

    default <T> ResponseEntity<T> badRequest(T body, HttpHeaders headers) {
        return respond(body, HttpStatus.BAD_REQUEST, headers);
    }

    /**
     * UNAUTHORIZED (401)
     *
     * @return
     */
    default ResponseEntity<?> unauthorized() {
        return unauthorized(null);
    }

    default <T> ResponseEntity<T> unauthorized(T body) {
        return unauthorized(body, new HttpHeaders());
    }

    default <T> ResponseEntity<T> unauthorized(T body, HttpHeaders headers) {
        return respond(body, HttpStatus.UNAUTHORIZED, headers);
    }

    /**
     * FORBIDDEN (403)
     *
     * @return
     */
    default ResponseEntity<?> forbidden() {
        return forbidden(null);
    }

    default <T> ResponseEntity<T> forbidden(T body) {
        return forbidden(body, new HttpHeaders());
    }

    default <T> ResponseEntity<T> forbidden(T body, HttpHeaders headers) {
        return respond(body, HttpStatus.FORBIDDEN, headers);
    }

    /**
     * NOT FOUND (404)
     *
     * @return
     */
    default ResponseEntity<?> notFound() {
        return notFound(null);
    }

    default <T> ResponseEntity<T> notFound(T body) {
        return notFound(body, new HttpHeaders());
    }

    default <T> ResponseEntity<T> notFound(T body, HttpHeaders headers) {
        return respond(body, HttpStatus.NOT_FOUND, headers);
    }

    /**
     * INTERNAL SERVER ERROR (500)
     *
     * @return
     */
    default ResponseEntity<?> internalServerError() {
        return internalServerError(null);
    }

    default <T> ResponseEntity<T> internalServerError(T body) {
        return internalServerError(body, new HttpHeaders());
    }

    default <T> ResponseEntity<T> internalServerError(T body, HttpHeaders headers) {
        return respond(body, HttpStatus.INTERNAL_SERVER_ERROR, headers);
    }

    /**
     * UNPROCESSABLE ENTITY (422)
     *
     * @return
     */
    default ResponseEntity<?> unprocessableEntity() {
        return unprocessableEntity(null);
    }

    default <T> ResponseEntity<T> unprocessableEntity(T body) {
        return unprocessableEntity(body, new HttpHeaders());
    }

    default <T> ResponseEntity<T> unprocessableEntity(T body, HttpHeaders headers) {
        return respond(body, HttpStatus.UNPROCESSABLE_ENTITY, headers);
    }

    /**
     * CONFLICT (409)
     *
     * @return
     */
    default ResponseEntity<?> conflict() {
        return conflict(null);
    }

    default <T> ResponseEntity<T> conflict(T body) {
        return conflict(body, new HttpHeaders());
    }

    default <T> ResponseEntity<T> conflict(T body, HttpHeaders headers) {
        return respond(body, HttpStatus.CONFLICT, headers);
    }

    /**
     * EXPECTATION_FAILED (417)
     *
     * @return
     */
    default ResponseEntity<?> expectationFailed() {
        return expectationFailed(null);
    }

    default <T> ResponseEntity<T> expectationFailed(T body) {
        return expectationFailed(body, new HttpHeaders());
    }

    default <T> ResponseEntity<T> expectationFailed(T body, HttpHeaders headers) {
        return respond(body, HttpStatus.EXPECTATION_FAILED, headers);
    }


    /**
     * @param httpStatus
     * @param <T>
     * @return
     */
    default <T> ResponseEntity<T> respond(HttpStatus httpStatus) {
        return respond(null, httpStatus);
    }

    /**
     * @param body
     * @param httpStatus
     * @param <T>
     * @return
     */
    default <T> ResponseEntity<T> respond(T body, HttpStatus httpStatus) {
        return respond(body, httpStatus, new HttpHeaders());
    }

    /**
     * @param body
     * @param httpStatus
     * @param headers
     * @param <T>
     * @return
     */
    default <T> ResponseEntity<T> respond(T body, HttpStatus httpStatus, HttpHeaders headers) {
        return ResponseEntity
                .status(httpStatus)
                .headers(headers)
                .body(body);
    }
}
