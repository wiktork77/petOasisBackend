package com.example.petoasisbackend.Mapper;

import com.example.petoasisbackend.DTO.Descriptor.HealthStatus.HealthStatusMinimumDTO;
import com.example.petoasisbackend.DTO.Descriptor.HealthStatus.HealthStatusNameDTO;
import com.example.petoasisbackend.DTO.Descriptor.HealthStatus.HealthStatusVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class HealthStatusMapper implements DetailLevelMapper<HealthStatus> {
    @Override
    public Function<HealthStatus, ModelDTO<HealthStatus>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return HealthStatusVerboseDTO::fromHealthStatus;
            }
            case MINIMUM -> {
                return HealthStatusMinimumDTO::fromHealthStatus;
            }
            default -> {
                return HealthStatusNameDTO::fromHealthStatus;
            }
        }
    }
}
