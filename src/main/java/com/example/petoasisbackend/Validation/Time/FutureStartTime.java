package com.example.petoasisbackend.Validation.Time;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FutureStartTimeValidator.class)
@Documented
public @interface FutureStartTime {
    String message() default "Start time must be in the future";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}