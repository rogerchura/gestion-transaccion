package com.bisa.cam.utils.spring.validators;

import com.bisa.cam.utils.spring.Error;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegexValidator extends Validator<String> {

    public RegexValidator(ValidationPredicate<String> validationPredicate) {
        super(validationPredicate);
    }

    @Override
    public <T> void validate(T validatable) throws ValidationException {

        final String regex = (String) validatable;

        Pattern pattern = Pattern.compile(validationPredicate.getValue());
        Matcher matcher = pattern.matcher(regex);
        if (!matcher.matches()) {
            throw new ValidationException(
                    Error.builder().
                            withMessage("El parametro '{}' no coincide con la Expresion Regular: '{}'",
                                    regex, validationPredicate.getValue()).
                            withField(validationPredicate.getName()).
                            build()
            );
        }
    }
}
