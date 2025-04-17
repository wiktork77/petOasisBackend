package com.example.petoasisbackend.Request.Animal;

import com.example.petoasisbackend.Model.Descriptor.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class AnimalUpdateRequest {
    @NotBlank(message = "must not be blank")
    private String name;

    @NotNull(message = "must not be null")
    @Positive(message = "must be greater than 0")
    private Float weight;

    @NotNull(message = "must not be null")
    @Positive(message = "must be greater than 0")
    private Float height;

    @NotNull(message = "must not be null")
    @Positive(message = "must be greater than 0")
    private Float length;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(example = "1990-12-15", type = "string", format = "date")
    private LocalDate dateOfBirth;

    private Boolean isNeutered;

    @NotNull(message = "must not be null")
    private Gender gender;

    @NotNull(message = "must not be null")
    private Boolean enjoysPetting;

    @NotBlank(message = "must not be blank")
    private String description;
}
