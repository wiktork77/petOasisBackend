package com.example.petoasisbackend.Request.Animal;

import com.example.petoasisbackend.Model.Enum.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AnimalAddRequest {
    @NotNull(message = "must not be null")
    private Long shelterId;

    @NotBlank(message = "must not be blank")
    private String name;

    @NotNull(message = "must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(example = "1990-12-15", type = "string", format = "date")
    private LocalDate dateOfBirth;

    @NotNull(message = "must not be null")
    @Positive(message = "must be greater than 0")
    private Float height;

    @NotNull(message = "must not be null")
    @Positive(message = "must be greater than 0")
    private Float length;

    @NotNull(message = "must not be null")
    @Positive(message = "must be greater than 0")
    private Float weight;

    private Boolean isNeutered;

    @NotNull(message = "must not be null")
    private Gender gender;

    @NotNull(message = "must not be null")
    private Boolean enjoysPetting;

    private String pictureUrl;

    @NotBlank(message = "must not be blank")
    private String description;

    @NotNull(message = "must not be null")
    private Integer healthStatusId;

    @NotNull(message = "must not be null")
    private Integer availabilityStatusId;
}
