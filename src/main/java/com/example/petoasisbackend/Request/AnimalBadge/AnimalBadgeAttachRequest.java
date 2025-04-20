package com.example.petoasisbackend.Request.AnimalBadge;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AnimalBadgeAttachRequest {
    @NotNull(message = "must not be null")
    private Long animalId;

    @NotNull(message = "must not be null")
    private Integer badgeId;
}
