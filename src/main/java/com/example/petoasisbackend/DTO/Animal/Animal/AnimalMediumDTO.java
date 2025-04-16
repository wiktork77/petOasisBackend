package com.example.petoasisbackend.DTO.Animal.Animal;

import com.example.petoasisbackend.DTO.Status.AvailabilityStatus.AvailabilityStatusNameDTO;
import com.example.petoasisbackend.DTO.Status.AvailabilityStatus.AvailabilityStatusVerboseDTO;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Descriptor.AnimalBadge;
import com.example.petoasisbackend.Model.Descriptor.Gender;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalMediumDTO {
    private Long animalId;
    private String name;
    private String pictureUrl;
    private Float rating;
    private Gender gender;
    private Set<AnimalBadge> animalBadges;
    private AvailabilityStatusVerboseDTO availabilityStatus;

    public static AnimalMediumDTO fromAnimal(Animal animal) {
        return new AnimalMediumDTO(
                animal.getAnimalId(),
                animal.getName(),
                animal.getPictureURL(),
                animal.getRating(),
                animal.getGender(),
                animal.getAnimalBadges(),
                AvailabilityStatusVerboseDTO.fromAvailabilityStatus(animal.getAvailabilityStatus())
        );
    }
}
