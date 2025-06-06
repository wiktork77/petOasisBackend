package com.example.petoasisbackend.DTO.Animal.Cat;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalConciseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Cat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CatConciseDTO implements ModelDTO<Cat> {
    private Long catId;

    private AnimalConciseDTO animal;

    public static CatConciseDTO fromCat(Cat cat) {
        return new CatConciseDTO(
                cat.getCatId(),
                AnimalConciseDTO.fromAnimal(cat.getAnimal())
        );
    }
}
