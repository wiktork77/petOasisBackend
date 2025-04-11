package com.example.petoasisbackend.DTO.Descriptor.HealthStatus;

import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HealthStatusMinimumDTO {
    private Integer healthId;

    private HealthStatusMinimumDTO(Integer healthId) {
        this.healthId = healthId;
    }

    public static HealthStatusMinimumDTO fromHealthStatus(HealthStatus status) {
        return new HealthStatusMinimumDTO(status.getHealthId());
    }
}
