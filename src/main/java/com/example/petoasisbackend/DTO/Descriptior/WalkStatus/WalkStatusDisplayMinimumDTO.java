package com.example.petoasisbackend.DTO.Descriptior.WalkStatus;

import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import lombok.Getter;

@Getter
public class WalkStatusDisplayMinimumDTO {
    private Integer statusId;

    public WalkStatusDisplayMinimumDTO(Integer statusId) {
        this.statusId = statusId;
    }

    public static WalkStatusDisplayMinimumDTO fromWalkStatus(WalkStatus status) {
        return new WalkStatusDisplayMinimumDTO(status.getStatusId());
    }
}
