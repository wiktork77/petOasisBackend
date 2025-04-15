package com.example.petoasisbackend.DTO.Status.WalkStatus;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Status.WalkStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WalkStatusMinimumDTO implements ModelDTO<WalkStatus> {
    private Integer statusId;

    private WalkStatusMinimumDTO(Integer statusId) {
        this.statusId = statusId;
    }


    public static WalkStatusMinimumDTO fromWalkStatus(WalkStatus status) {
        return new WalkStatusMinimumDTO(status.getStatusId());
    }

}
