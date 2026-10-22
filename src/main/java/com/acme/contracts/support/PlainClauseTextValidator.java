package com.acme.contracts.support;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class PlainClauseTextValidator implements ConstraintValidator<PlainClauseText, String> {

    private static final Pattern ALLOWED = Pattern.compile("[\\p{L}\\p{N} .,;:()'\\n-]*");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || ALLOWED.matcher(value).matches();
    }
}
