package com.example.petoasisbackend.Validation.Time;

import com.example.petoasisbackend.Request.Activity.Walk.WalkRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class FutureStartTimeValidator implements ConstraintValidator<FutureStartTime, WalkRequest> {

    @Override
    public boolean isValid(WalkRequest request, ConstraintValidatorContext context) {
        return request.getStartTime().isAfter(LocalDateTime.now());
    }
}