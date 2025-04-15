package com.example.petoasisbackend.Request.Status.AvailabilityStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AvailabilityStatusAddRequest {
    @NotBlank(message = "must not be blank")
    private String availability;
}
