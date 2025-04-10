package com.example.petoasisbackend.DTO.Descriptor.AvailabilityStatus;

import com.example.petoasisbackend.Model.AnimalStatus.AvailabilityStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AvailabilityStatusNameDTO {
    private String availability;

    public AvailabilityStatusNameDTO(String availability) {
        this.availability = availability;
    }

    public static AvailabilityStatusNameDTO fromAvailabilityStatus(AvailabilityStatus status) {
        return new AvailabilityStatusNameDTO(status.getAvailability());
    }
}
