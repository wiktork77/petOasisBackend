package com.example.petoasisbackend.Request.Activity.Walk;

import com.example.petoasisbackend.Validation.Time.FutureStartTime;
import com.example.petoasisbackend.Validation.Time.ValidWalkTime;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ValidWalkTime
public class WalkRequest {
    @NotNull(message = "must not be null")
    @FutureStartTime(message = "must be in future")
    private LocalDateTime startTime;

    @NotNull(message = "must not be null")
    private LocalDateTime endTime;
}
