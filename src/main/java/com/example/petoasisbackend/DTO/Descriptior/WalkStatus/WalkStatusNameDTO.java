package com.example.petoasisbackend.DTO.Descriptior.WalkStatus;

import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WalkStatusNameDTO {
    private String status;

    public WalkStatusNameDTO(String status) {
        this.status = status;
    }


    public static WalkStatusNameDTO fromWalkStatus(WalkStatus status) {
        return new WalkStatusNameDTO(status.getStatus());
    }
}
