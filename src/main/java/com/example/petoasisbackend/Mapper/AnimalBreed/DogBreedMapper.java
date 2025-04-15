package com.example.petoasisbackend.Mapper.AnimalBreed;

import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedMinimumDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedNameDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Mapper.DetailLevelMapper;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DogBreedMapper implements DetailLevelMapper<DogBreed> {
    @Override
    public Function<DogBreed, ModelDTO<DogBreed>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return DogBreedVerboseDTO::fromDogBreed;
            }
            case MINIMUM -> {
                return DogBreedMinimumDTO::fromDogBreed;
            }
            default -> {
                return DogBreedNameDTO::fromDogBreed;
            }
        }
    }
}
