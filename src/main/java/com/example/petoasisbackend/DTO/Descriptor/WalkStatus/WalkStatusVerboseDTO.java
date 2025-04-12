package com.example.petoasisbackend.DTO.Descriptor.WalkStatus;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Status.WalkStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WalkStatusVerboseDTO implements ModelDTO<WalkStatus> {
    private Integer statusId;
    private String status;

    private WalkStatusVerboseDTO(Integer statusId, String status) {
        this.statusId = statusId;
        this.status = status;
    }

    public static WalkStatusVerboseDTO fromWalkStatus(WalkStatus status) {
        return new WalkStatusVerboseDTO(
                status.getStatusId(),
                status.getStatus()
        );
    }
}
