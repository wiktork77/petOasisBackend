package com.example.petoasisbackend.Mapper.Animal;

import com.example.petoasisbackend.DTO.Animal.Cat.CatConciseDTO;
import com.example.petoasisbackend.DTO.Animal.Cat.CatMediumDTO;
import com.example.petoasisbackend.DTO.Animal.Cat.CatMinimumDTO;
import com.example.petoasisbackend.DTO.Animal.Cat.CatVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Mapper.DetailLevelMapper;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CatMapper implements DetailLevelMapper<Cat> {
    @Override
    public Function<Cat, ModelDTO<Cat>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return CatVerboseDTO::fromCat;
            }
            case MEDIUM -> {
                return CatMediumDTO::fromCat;
            }
            case CONCISE -> {
                return CatConciseDTO::fromCat;
            }
            case MINIMUM -> {
                return CatMinimumDTO::fromCat;
            }
            default -> {
                return CatMinimumDTO::fromCat;
            }
        }
    }
}
