package com.move24.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValidation, String> {

    private EnumValidation enumValidation;
    @Override
    public void initialize(final EnumValidation constraintAnnotation) {
        this.enumValidation = constraintAnnotation;
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        final Enum<?>[] enumConstants = this.enumValidation.enumClass().getEnumConstants();
        if (enumConstants == null) {
            return false;
        }

        return Arrays.stream(enumConstants)
                .anyMatch(enumConstant -> convertible(value, enumConstant) || convertibleIgnoreCase(value, enumConstant));
    }

    private boolean convertibleIgnoreCase(final String value, final Enum<?> enumConstant) {
        return this.enumValidation.ignoreCase() && value.trim().equalsIgnoreCase(enumConstant.name());
    }

    private boolean convertible(final String value, final Enum<?> enumConstant) {
        return value.trim().equals(enumConstant.name());
    }
}
