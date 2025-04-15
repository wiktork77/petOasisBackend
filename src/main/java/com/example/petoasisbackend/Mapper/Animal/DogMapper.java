package com.example.petoasisbackend.Mapper.Animal;

import com.example.petoasisbackend.DTO.Animal.Dog.DogConciseDTO;
import com.example.petoasisbackend.DTO.Animal.Dog.DogMediumDTO;
import com.example.petoasisbackend.DTO.Animal.Dog.DogMinimumDTO;
import com.example.petoasisbackend.DTO.Animal.Dog.DogVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Mapper.DetailLevelMapper;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DogMapper implements DetailLevelMapper<Dog> {
    @Override
    public Function<Dog, ModelDTO<Dog>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return DogVerboseDTO::fromDog;
            }
            case MEDIUM -> {
                return DogMediumDTO::fromDog;
            }
            case CONCISE -> {
                return DogConciseDTO::fromDog;
            }
            case MINIMUM -> {
                return DogMinimumDTO::fromDog;
            }
            default -> {
                return DogMinimumDTO::fromDog;
            }
        }
    }
}
