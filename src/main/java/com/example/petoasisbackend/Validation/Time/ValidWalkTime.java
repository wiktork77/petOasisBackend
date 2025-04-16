package com.example.petoasisbackend.Validation.Time;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = WalkTimeValidator.class)
@Documented
public @interface ValidWalkTime {
    String message() default "endTime must be after startTime";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
