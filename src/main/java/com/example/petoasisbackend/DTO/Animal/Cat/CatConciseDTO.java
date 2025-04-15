package com.example.petoasisbackend.DTO.Animal.Cat;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Animal.Cat;

public class CatConciseDTO implements ModelDTO<Cat> {
    private Long catId;

    public static CatConciseDTO fromCat(Cat cat) {
        return null;
    }
}
