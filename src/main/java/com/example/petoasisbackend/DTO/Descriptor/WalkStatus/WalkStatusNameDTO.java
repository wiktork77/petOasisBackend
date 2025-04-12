package com.example.petoasisbackend.DTO.Descriptor.WalkStatus;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Status.WalkStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WalkStatusNameDTO implements ModelDTO<WalkStatus> {
    private String status;

    private WalkStatusNameDTO(String status) {
        this.status = status;
    }


    public static WalkStatusNameDTO fromWalkStatus(WalkStatus status) {
        return new WalkStatusNameDTO(status.getStatus());
    }
}
