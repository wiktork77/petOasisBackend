package com.example.petoasisbackend.DTO.Animal.Cat;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.Descriptor.AnimalBadge;
import com.example.petoasisbackend.Model.Descriptor.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatConciseDTO implements ModelDTO<Cat> {
    private Long dogId;
    private String name;
    private String pictureUrl;
    private Float rating;
    private Gender gender;
    private Set<AnimalBadge> animalBadges;

    public static CatConciseDTO fromCat(Cat cat) {
        return new CatConciseDTO(
                cat.getCatId(),
                cat.getAnimal().getName(),
                cat.getAnimal().getPictureURL(),
                cat.getAnimal().getRating(),
                cat.getAnimal().getGender(),
                cat.getAnimal().getAnimalBadges()
        );
    }
}
