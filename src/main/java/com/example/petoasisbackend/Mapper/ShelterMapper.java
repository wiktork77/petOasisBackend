package com.example.petoasisbackend.Mapper;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterConciseDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterMediumDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterMinimumDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterVerboseDTO;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Request.DataDetailLevel;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ShelterMapper implements DetailLevelMapper<Shelter> {
    @Override
    public Function<Shelter, ModelDTO<Shelter>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return ShelterVerboseDTO::fromShelter;
            }
            case MEDIUM -> {
                return ShelterMediumDTO::fromShelter;
            }
            case CONCISE -> {
                return ShelterConciseDTO::fromShelter;
            }
            case MINIMUM -> {
                return ShelterMinimumDTO::fromShelter;
            }
            default -> {
                return ShelterMinimumDTO::fromShelter;
            }
        }
    }
}
