package com.example.petoasisbackend.Request.Animal.Cat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CatUpdateRequest {
    @NotNull(message = "must not be null")
    private Boolean isDeclawed;

    @NotNull(message = "must not be null")
    @PositiveOrZero(message = "must be greater or equal to 0")
    private Integer vocalizationLevel;

    private String favoriteTreat;
}
