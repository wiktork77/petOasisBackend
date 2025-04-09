package com.example.petoasisbackend.DTO.AvailabilityStatus;

import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AvailabilityStatusMinimumDTO {
    private Integer availabilityId;

    public AvailabilityStatusMinimumDTO(Integer availabilityId) {
        this.availabilityId = availabilityId;
    }

    public static AvailabilityStatusMinimumDTO fromAvailabilityStatus(AvailabilityStatus status) {
        return new AvailabilityStatusMinimumDTO(status.getAvailabilityId());
    }
}
