package com.example.petoasisbackend.Mapper.Animal;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalConciseDTO;
import com.example.petoasisbackend.DTO.Animal.Animal.AnimalMediumDTO;
import com.example.petoasisbackend.DTO.Animal.Animal.AnimalMinimumDTO;
import com.example.petoasisbackend.DTO.Animal.Animal.AnimalVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Mapper.DetailLevelMapper;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AnimalMapper implements DetailLevelMapper<Animal> {
    @Override
    public Function<Animal, ModelDTO<Animal>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return AnimalVerboseDTO::fromAnimal;
            }
            case MEDIUM -> {
                return AnimalMediumDTO::fromAnimal;
            }
            case CONCISE -> {
                return AnimalConciseDTO::fromAnimal;
            }
            case MINIMUM -> {
                return AnimalMinimumDTO::fromAnimal;
            }
            default -> {
                return AnimalMinimumDTO::fromAnimal;
            }
        }
    }
}
