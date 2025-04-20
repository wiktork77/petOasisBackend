package com.example.petoasisbackend.Mapper.Descriptor;

import com.example.petoasisbackend.DTO.Descriptor.AnimalBadge.AnimalBadgeConciseDTO;
import com.example.petoasisbackend.DTO.Descriptor.AnimalBadge.AnimalBadgeMediumDTO;
import com.example.petoasisbackend.DTO.Descriptor.AnimalBadge.AnimalBadgeMinimumDTO;
import com.example.petoasisbackend.DTO.Descriptor.AnimalBadge.AnimalBadgeVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Mapper.DetailLevelMapper;
import com.example.petoasisbackend.Model.Descriptor.AnimalBadge;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AnimalBadgeMapper implements DetailLevelMapper<AnimalBadge> {
    @Override
    public Function<AnimalBadge, ModelDTO<AnimalBadge>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return AnimalBadgeVerboseDTO::fromAnimalBadge;
            }
            case MEDIUM -> {
                return AnimalBadgeMediumDTO::fromAnimalBadge;
            }
            case CONCISE -> {
                return AnimalBadgeConciseDTO::fromAnimalBadge;
            }
            case MINIMUM -> {
                return AnimalBadgeMinimumDTO::fromAnimalBadge;
            }
            default -> {
                return AnimalBadgeMinimumDTO::fromAnimalBadge;
            }
        }
    }
}
