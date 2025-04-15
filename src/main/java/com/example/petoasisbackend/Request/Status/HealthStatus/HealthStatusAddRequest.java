package com.example.petoasisbackend.Request.Status.HealthStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class HealthStatusAddRequest {
    @NotBlank(message = "must not be blank")
    private String healthStatus;
}
