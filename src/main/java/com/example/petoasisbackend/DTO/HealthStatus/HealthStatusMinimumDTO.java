package com.example.petoasisbackend.DTO.HealthStatus;

import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HealthStatusMinimumDTO {
    private Integer healthId;

    public HealthStatusMinimumDTO(Integer healthId) {
        this.healthId = healthId;
    }

    public static HealthStatusMinimumDTO fromHealthStatus(HealthStatus status) {
        return new HealthStatusMinimumDTO(status.getHealthId());
    }
}
