package com.example.petoasisbackend.Request.Animal.Dog;

import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedMinimumDTO;
import com.example.petoasisbackend.DTO.Status.AvailabilityStatus.AvailabilityStatusMinimumDTO;
import com.example.petoasisbackend.DTO.Status.HealthStatus.HealthStatusMinimumDTO;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Model.Descriptor.Gender;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import com.example.petoasisbackend.Request.Animal.AnimalAddRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class DogAddRequest extends AnimalAddRequest {
    @NotNull(message = "must not be null")
    private Boolean isMuzzleRequired;

    @NotNull(message = "must not be null")
    @PositiveOrZero(message = "must be greater than or equal to 0")
    private Integer barkingLevel;

    private String favoriteToy;

    @NotNull(message = "must not be null")
    private Integer dogBreedId;
}
