package com.example.petoasisbackend.DTO.Animal.Animal;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Badge.AnimalBadge;
import com.example.petoasisbackend.Model.Enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalConciseDTO implements ModelDTO<Animal> {
    private Long animalId;
    private String name;
    private String pictureUrl;
    private Float rating;
    private Gender gender;
    private Set<AnimalBadge> animalBadges;

    public static AnimalConciseDTO fromAnimal(Animal animal) {
        return new AnimalConciseDTO(
                animal.getAnimalId(),
                animal.getName(),
                animal.getPictureURL(),
                animal.getRating(),
                animal.getGender(),
                animal.getAnimalBadges()
        );
    }
}
