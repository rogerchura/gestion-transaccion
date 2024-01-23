package com.bisa.cam.utils.spring.validators;


public abstract class Validator<V> {
    protected final ValidationPredicate<V> validationPredicate;

    protected Validator(ValidationPredicate<V> validationPredicate) {
        this.validationPredicate = validationPredicate;
    }

    /**
     * @param validatable
     * @throws ValidationException
     */
    public abstract <T> void validate(T validatable) throws ValidationException;
}
