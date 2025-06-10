package com.studiop.dormmanagementsystem.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class ValueOfEnumValidator implements ConstraintValidator<EnumValue, String> {

    private EnumValue enumValue;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        this.enumValue = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        Enum<?>[] enumValues = this.enumValue.enumClass().getEnumConstants();

        return Arrays.stream(enumValues).anyMatch(eVal -> isValueValid(value, eVal));
    }

    private boolean isValueValid(String value, Enum<?> eVal) {
        return value.equals(eVal.toString())
                || (this.enumValue.ignoreCase() && value.equalsIgnoreCase(eVal.toString()));
    }
}
