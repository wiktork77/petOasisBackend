package com.example.petoasisbackend.Mapper.Status;

import com.example.petoasisbackend.DTO.Status.WalkStatus.WalkStatusMinimumDTO;
import com.example.petoasisbackend.DTO.Status.WalkStatus.WalkStatusNameDTO;
import com.example.petoasisbackend.DTO.Status.WalkStatus.WalkStatusVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Mapper.DetailLevelMapper;
import com.example.petoasisbackend.Model.Status.WalkStatus;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class WalkStatusMapper implements DetailLevelMapper<WalkStatus> {
    @Override
    public Function<WalkStatus, ModelDTO<WalkStatus>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return WalkStatusVerboseDTO::fromWalkStatus;
            }
            case MINIMUM -> {
                return WalkStatusMinimumDTO::fromWalkStatus;
            }
            default -> {
                return WalkStatusNameDTO::fromWalkStatus;
            }
        }
    }
}
