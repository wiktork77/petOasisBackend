package com.example.petoasisbackend.Request.Animal.Dog;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class DogUpdateRequest {
    @NotNull(message = "must not be null")
    private Boolean isMuzzleRequired;

    @NotNull(message = "must not be null")
    @PositiveOrZero(message = "must be greater than or equal to 0")
    private Integer barkingLevel;
    
    private String favoriteToy;
}
