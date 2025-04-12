package com.example.petoasisbackend.DTO.Descriptor.HealthStatus;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HealthStatusMinimumDTO implements ModelDTO<HealthStatus> {
    private Integer healthId;

    private HealthStatusMinimumDTO(Integer healthId) {
        this.healthId = healthId;
    }

    public static HealthStatusMinimumDTO fromHealthStatus(HealthStatus status) {
        return new HealthStatusMinimumDTO(status.getHealthId());
    }
}
