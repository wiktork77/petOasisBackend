package com.example.petoasisbackend.DTO.Status.HealthStatus;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class HealthStatusNameDTO implements ModelDTO<HealthStatus> {
    private String healthStatus;

    private HealthStatusNameDTO(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public static HealthStatusNameDTO fromHealthStatus(HealthStatus status) {
        return new HealthStatusNameDTO(status.getHealthStatus());
    }
}
