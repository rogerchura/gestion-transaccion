package com.bisa.cam.utils.spring.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

@JsonPropertyOrder({"code", "field", "message"})
public class ErrorPayload<C> extends Payload {

    /**
     * The code.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final C code;

    /**
     * The message.
     */
    private final String message;

    /**
     * A possible related field
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final String field;


    // region Constructor, getters, equals, hashCode, toString, builder -- generated by BoB the Builder of Beans
    // The code below has been generated by BoB the Builder of Beans based on the class' fields.
    // Everything after this comment will be regenerated if you invoke BoB again.
    // If you don't know who BoB is, you can find him here: https://bitbucket.org/atlassianlabs/bob-the-builder-of-beans

    @JsonCreator
    public ErrorPayload(@JsonProperty("code") C code, @JsonProperty("message") String message) {
        this(code, message, null);
    }

    @JsonCreator
    public ErrorPayload(@JsonProperty("code") C code, @JsonProperty("message") String message, @JsonProperty("field") String field) {
        this(code, message, field, null, null);
    }

    @JsonCreator
    public ErrorPayload(@JsonProperty("code") C code, @JsonProperty("message") String message, @JsonProperty("field") String field, @JsonProperty("uuid") UUID uuid, @JsonProperty("timestamp") LocalDateTime timestamp) {
        super(uuid, timestamp, null);
        this.code = code;
        this.message = message;
        this.field = field;
    }

    // region Getters -- generated by BoB the Builder of Beans
    public C getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }// endregion Getters

    // region hashCode() and equals() -- generated by BoB the Builder of Beans
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ErrorPayload that = (ErrorPayload) o;

        return Objects.equals(this.getCode(), that.getCode()) && Objects.equals(this.getMessage(), that.getMessage()) && Objects.equals(this.getField(), that.getField()) && Objects.equals(this.getUuid(), that.getUuid()) && Objects.equals(this.getTimestamp(), that.getTimestamp()) && Objects.equals(this.getData(), that.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getMessage(), getField(), getUuid(), getTimestamp(), getData());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", this.getClass().getSimpleName() + "[", "]")
                .add("code=" + getCode()).add("message=" + getMessage()).add("field=" + getField()).add("uuid=" + getUuid()).add("timestamp=" + getTimestamp()).add("data=" + getData()).toString();
    }// endregion hashCode() and equals()

    // endregion Constructor, getters, equals, hashCode, toString, builder
}
