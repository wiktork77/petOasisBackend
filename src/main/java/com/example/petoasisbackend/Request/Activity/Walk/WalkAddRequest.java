package com.example.petoasisbackend.Request.Activity.Walk;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class WalkAddRequest {
    @NotNull(message = "must not be null")
    private Long animalId;

    @NotNull(message = "must not be null")
    private Long personId;

    @NotNull(message = "must not be null")
    private Long shelterId;

    @NotNull(message = "must not be null")
    private LocalDateTime startTime;

    @NotNull(message = "must not be null")
    private LocalDateTime endTime;
}
