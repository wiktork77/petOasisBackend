package com.example.petoasisbackend.DTO.Status.HealthStatus;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HealthStatusVerboseDTO implements ModelDTO<HealthStatus> {
    private Integer healthId;
    private String healthStatus;

    private HealthStatusVerboseDTO(Integer healthId, String healthStatus) {
        this.healthId = healthId;
        this.healthStatus = healthStatus;
    }

    public static HealthStatusVerboseDTO fromHealthStatus(HealthStatus status) {
        return new HealthStatusVerboseDTO(
                status.getHealthId(),
                status.getHealthStatus()
        );
    }
}
