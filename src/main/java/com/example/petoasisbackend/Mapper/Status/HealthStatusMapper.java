package com.example.petoasisbackend.Mapper.Status;

import com.example.petoasisbackend.DTO.Status.HealthStatus.HealthStatusMinimumDTO;
import com.example.petoasisbackend.DTO.Status.HealthStatus.HealthStatusNameDTO;
import com.example.petoasisbackend.DTO.Status.HealthStatus.HealthStatusVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Mapper.DetailLevelMapper;
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
