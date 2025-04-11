package com.example.petoasisbackend.DTO.Descriptor.WalkStatus;

import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WalkStatusMinimumDTO {
    private Integer statusId;

    private WalkStatusMinimumDTO(Integer statusId) {
        this.statusId = statusId;
    }


    public static WalkStatusMinimumDTO fromWalkStatus(WalkStatus status) {
        return new WalkStatusMinimumDTO(status.getStatusId());
    }

}
