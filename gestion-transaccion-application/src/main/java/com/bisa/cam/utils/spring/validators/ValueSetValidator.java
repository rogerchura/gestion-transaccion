package com.bisa.cam.utils.spring.validators;

import com.bisa.cam.utils.spring.Error;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


public class ValueSetValidator<T> extends Validator<T> {

    public ValueSetValidator(ValidationPredicate<T> validationPredicate) {
        super(validationPredicate);
    }

    @Override
    public <T> void validate(T validatable) throws ValidationException {
        Collection<T> allowedValues = (Collection<T>) validationPredicate.getValue();

        final String value = (String) validatable;

        Optional<T> found = allowedValues.stream().filter(s -> s.equals(value)).
                findFirst();

        if (!found.isPresent())
            throw new ValidationException(

                    Error.builder().
                            withMessage("El valor '{}' no esta en la lista de valores esperados: {{}}",
                                    value,
                                    allowedValues.stream().
                                            map(c -> String.valueOf(c)).collect(Collectors.joining(", "))).
                            withField(validationPredicate.getName()).
                            build()
            );
    }
}
