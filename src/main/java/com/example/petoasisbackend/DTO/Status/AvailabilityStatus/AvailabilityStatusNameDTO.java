package com.example.petoasisbackend.DTO.Status.AvailabilityStatus;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AvailabilityStatusNameDTO implements ModelDTO<AvailabilityStatus> {
    private String availability;

    private AvailabilityStatusNameDTO(String availability) {
        this.availability = availability;
    }

    public static AvailabilityStatusNameDTO fromAvailabilityStatus(AvailabilityStatus status) {
        return new AvailabilityStatusNameDTO(status.getAvailability());
    }
}
