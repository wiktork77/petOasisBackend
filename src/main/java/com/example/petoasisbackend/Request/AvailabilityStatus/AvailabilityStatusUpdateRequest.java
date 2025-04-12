package com.example.petoasisbackend.Request.AvailabilityStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AvailabilityStatusUpdateRequest {
    @NotBlank(message = "must not be blank")
    private String availability;
}
