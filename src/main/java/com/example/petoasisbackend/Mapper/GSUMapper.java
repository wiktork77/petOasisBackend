package com.example.petoasisbackend.Mapper;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUConciseDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUMediumDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUMinimumDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUVerboseDTO;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class GSUMapper implements DetailLevelMapper<GeneralSystemUser> {
    @Override
    public Function<GeneralSystemUser, ModelDTO<GeneralSystemUser>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return GSUVerboseDTO::fromGSU;
            }
            case MEDIUM -> {
                return GSUMediumDTO::fromGSU;
            }
            case CONCISE -> {
                return GSUConciseDTO::fromGSU;
            }
            case MINIMUM -> {
                return GSUMinimumDTO::fromGSU;
            }
            default -> {
                return GSUMinimumDTO::fromGSU;
            }
        }
    }
}
