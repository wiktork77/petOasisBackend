package com.example.petoasisbackend.Request.Animal.Dog;

import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Model.Descriptor.Gender;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
public class DogAddRequest {
    private Long shelterId;

    private String name;

    private LocalDate birthDate;

    private Float height;

    private Float length;

    private Float weight;

    private Boolean isNeutered;

    private Boolean isMuzzleRequired;

    private Byte barkingLevel;

    private DogBreed dogBreed;

    private String favoriteToy;

    private Gender gender;

    private Boolean enjoysPetting;

    private String pictureUrl;

    private HealthStatus healthStatus;

    private AvailabilityStatus availabilityStatus;

    private String description;

}
