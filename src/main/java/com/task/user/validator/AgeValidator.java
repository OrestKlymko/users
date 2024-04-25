package com.task.user.validator;

import com.task.user.validator.annotation.MinAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;

public class AgeValidator implements ConstraintValidator<MinAge, LocalDate> {

    @Value("${requirement.min-age}")
    private int minAge;


    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return LocalDate.now().minusYears(minAge).isAfter(value) || LocalDate.now().minusYears(minAge).isEqual(value);
    }
}
