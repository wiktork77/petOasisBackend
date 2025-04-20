package com.example.petoasisbackend.Request.Animal.Cat;

import com.example.petoasisbackend.Request.Animal.AnimalAddRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CatAddRequest extends AnimalAddRequest {
    @NotNull(message = "must not be null")
    private Boolean isDeclawed;

    @NotNull(message = "must not be null")
    @PositiveOrZero(message = "must be greater than or equal to 0")
    private Integer vocalizationLevel;

    private String favoriteTreat;

    @NotNull(message = "must not be null")
    private Integer catBreedId;
}
