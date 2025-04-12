package com.example.petoasisbackend.DTO.Descriptor.AvailabilityStatus;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Status.AvailabilityStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AvailabilityStatusVerboseDTO implements ModelDTO<AvailabilityStatus> {
    private Integer availabilityId;
    private String availability;

    private AvailabilityStatusVerboseDTO(Integer availabilityId, String availability) {
        this.availabilityId = availabilityId;
        this.availability = availability;
    }

    public static AvailabilityStatusVerboseDTO fromAvailabilityStatus(AvailabilityStatus status) {
        return new AvailabilityStatusVerboseDTO(
                status.getAvailabilityId(),
                status.getAvailability()
        );
    }
}
