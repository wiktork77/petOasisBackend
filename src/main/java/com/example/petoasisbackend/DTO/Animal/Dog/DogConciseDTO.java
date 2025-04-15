package com.example.petoasisbackend.DTO.Animal.Dog;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Model.Descriptor.AnimalBadge;
import com.example.petoasisbackend.Model.Descriptor.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class DogConciseDTO implements ModelDTO<Dog> {
    private Long dogId;
    private String name;
    private String pictureUrl;
    private Float rating;
    private Gender gender;
    private Set<AnimalBadge> animalBadges;

    public static DogConciseDTO fromDog(Dog dog) {
        return new DogConciseDTO(
                dog.getDogId(),
                dog.getAnimal().getName(),
                dog.getAnimal().getPictureURL(),
                dog.getAnimal().getRating(),
                dog.getAnimal().getGender(),
                dog.getAnimal().getAnimalBadges()
        );
    }
}
