package com.example.petoasisbackend.Mapper.Activity.Walk;

import com.example.petoasisbackend.DTO.Activity.Walk.WalkConciseDTO;
import com.example.petoasisbackend.DTO.Activity.Walk.WalkMediumDTO;
import com.example.petoasisbackend.DTO.Activity.Walk.WalkMinimumDTO;
import com.example.petoasisbackend.DTO.Activity.Walk.WalkVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Mapper.DetailLevelMapper;
import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class WalkMapper implements DetailLevelMapper<Walk> {
    @Override
    public Function<Walk, ModelDTO<Walk>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return WalkVerboseDTO::fromWalk;
            }
            case MEDIUM -> {
                return WalkMediumDTO::fromWalk;
            }
            case CONCISE -> {
                return WalkConciseDTO::fromWalk;
            }
            case MINIMUM -> {
                return WalkMinimumDTO::fromWalk;
            }
            default -> {
                return WalkMinimumDTO::fromWalk;
            }
        }
    }
}
