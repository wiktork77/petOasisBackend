package com.example.petoasisbackend.Mapper.Status;

import com.example.petoasisbackend.DTO.Status.AvailabilityStatus.AvailabilityStatusMinimumDTO;
import com.example.petoasisbackend.DTO.Status.AvailabilityStatus.AvailabilityStatusNameDTO;
import com.example.petoasisbackend.DTO.Status.AvailabilityStatus.AvailabilityStatusVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Mapper.DetailLevelMapper;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AvailabilityStatusMapper implements DetailLevelMapper<AvailabilityStatus> {
    @Override
    public Function<AvailabilityStatus, ModelDTO<AvailabilityStatus>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return AvailabilityStatusVerboseDTO::fromAvailabilityStatus;
            }
            case MINIMUM -> {
                return AvailabilityStatusMinimumDTO::fromAvailabilityStatus;
            }
            default -> {
                return AvailabilityStatusNameDTO::fromAvailabilityStatus;
            }
        }
    }
}
