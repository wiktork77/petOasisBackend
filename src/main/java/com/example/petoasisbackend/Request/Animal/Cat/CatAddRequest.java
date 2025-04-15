package com.example.petoasisbackend.Request.Animal.Cat;

import com.example.petoasisbackend.Model.Descriptor.Gender;
import com.example.petoasisbackend.Request.Animal.AnimalAddRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
