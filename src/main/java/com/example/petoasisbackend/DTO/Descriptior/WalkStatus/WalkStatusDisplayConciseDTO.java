package com.example.petoasisbackend.DTO.Descriptior.WalkStatus;

import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import lombok.Getter;

@Getter
public class WalkStatusDisplayConciseDTO {
    private String status;

    public WalkStatusDisplayConciseDTO(String status) {
        this.status = status;
    }

    public static WalkStatusDisplayConciseDTO fromWalkStatus(WalkStatus status) {
        return new WalkStatusDisplayConciseDTO(status.getStatus());
    }
}
