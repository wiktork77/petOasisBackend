package com.example.petoasisbackend.DTO.Animal.Cat;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CatVerboseDTO implements ModelDTO<Cat> {
    private Long catId;
    private Animal animal;
    private Boolean isDeclawed;
    private Integer vocalizationLevel;
    private String favoriteTreat;
    private CatBreed catBreed;

    public static CatVerboseDTO fromCat(Cat cat) {
        return new CatVerboseDTO(
                cat.getCatId(),
                cat.getAnimal(),
                cat.getIsDeclawed(),
                cat.getVocalizationLevel(),
                cat.getFavoriteTreat(),
                cat.getCatBreed()
        );
    }
}
