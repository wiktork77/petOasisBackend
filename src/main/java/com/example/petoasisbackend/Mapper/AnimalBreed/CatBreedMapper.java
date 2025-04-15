package com.example.petoasisbackend.Mapper.AnimalBreed;

import com.example.petoasisbackend.DTO.AnimalBreed.Cat.CatBreedMinimumDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Cat.CatBreedNameDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Cat.CatBreedVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Mapper.DetailLevelMapper;
import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CatBreedMapper implements DetailLevelMapper<CatBreed> {
    @Override
    public Function<CatBreed, ModelDTO<CatBreed>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return CatBreedVerboseDTO::fromCatBreed;
            }
            case MINIMUM -> {
                return CatBreedMinimumDTO::fromCatBreed;
            }
            default -> {
                return CatBreedNameDTO::fromCatBreed;
            }
        }
    }
}
