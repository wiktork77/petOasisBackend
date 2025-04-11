package com.example.petoasisbackend.DTO.Descriptor.HealthStatus;

import com.example.petoasisbackend.Model.AnimalStatus.HealthStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class HealthStatusNameDTO {
    private String healthStatus;

    private HealthStatusNameDTO(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public static HealthStatusNameDTO fromHealthStatus(HealthStatus status) {
        return new HealthStatusNameDTO(status.getHealthStatus());
    }
}
