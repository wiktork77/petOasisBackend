package com.example.petoasisbackend.Validation.Time;

import com.example.petoasisbackend.Request.Activity.Walk.WalkRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class FutureStartTimeValidator implements ConstraintValidator<FutureStartTime, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime start, ConstraintValidatorContext context) {
        if (start == null) return true;
        return start.isAfter(LocalDateTime.now());
    }
}