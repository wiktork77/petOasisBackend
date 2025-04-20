package com.example.petoasisbackend.DTO.Animal.Animal;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.Status.AvailabilityStatus.AvailabilityStatusVerboseDTO;
import com.example.petoasisbackend.DTO.Status.HealthStatus.HealthStatusVerboseDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterMinimumDTO;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Enum.AnimalType;
import com.example.petoasisbackend.Model.Badge.AnimalBadge;
import com.example.petoasisbackend.Model.Enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalVerboseDTO implements ModelDTO<Animal> {
    private Long animalId;

    private String name;

    private Float weight;

    private Float height;

    private Float length;

    private LocalDate dateOfBirth;

    private Boolean isNeutered;

    private String description;

    private Gender gender;

    private Boolean enjoysPetting;

    private Float rating;

    private String pictureURL;

    private HealthStatusVerboseDTO healthStatus;

    private AvailabilityStatusVerboseDTO availabilityStatus;

    private Set<AnimalBadge> animalBadges;

    private AnimalType type;

    private Long parentId;

    private ShelterMinimumDTO home;
    public static AnimalVerboseDTO fromAnimal(Animal animal) {
        return new AnimalVerboseDTO(
                animal.getAnimalId(),
                animal.getName(),
                animal.getWeight(),
                animal.getHeight(),
                animal.getLength(),
                animal.getDateOfBirth(),
                animal.getIsNeutered(),
                animal.getDescription(),
                animal.getGender(),
                animal.getEnjoysPetting(),
                animal.getRating(),
                animal.getPictureURL(),
                HealthStatusVerboseDTO.fromHealthStatus(animal.getHealthStatus()),
                AvailabilityStatusVerboseDTO.fromAvailabilityStatus(animal.getAvailabilityStatus()),
                animal.getAnimalBadges(),
                animal.getType(),
                animal.getParentId(),
                ShelterMinimumDTO.fromShelter(animal.getHome())
        );
    }
}
