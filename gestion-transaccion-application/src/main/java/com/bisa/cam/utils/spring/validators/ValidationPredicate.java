package com.bisa.cam.utils.spring.validators;


public final class ValidationPredicate<V> {
    private final String name;
    private final V value;

    public ValidationPredicate(String name, V value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public V getValue() {
        return value;
    }
}
