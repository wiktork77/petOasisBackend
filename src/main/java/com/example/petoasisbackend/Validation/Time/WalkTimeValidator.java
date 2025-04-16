package com.example.petoasisbackend.Validation.Time;

import com.example.petoasisbackend.Request.Activity.Walk.WalkRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WalkTimeValidator implements ConstraintValidator<ValidWalkTime, WalkRequest> {
    @Override
    public boolean isValid(WalkRequest request, ConstraintValidatorContext context) {
        if (request.getStartTime() == null || request.getEndTime() == null) {
            return true;
        }
        return request.getEndTime().isAfter(request.getStartTime());
    }
}