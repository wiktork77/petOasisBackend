package com.example.petoasisbackend.DTO.Activity.Walk;


import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WalkWithStatusDTO {
    private Long walkId;
    private WalkStatus status;

    private WalkWithStatusDTO(Long walkId, WalkStatus status) {
        this.walkId = walkId;
        this.status = status;
    }

    public static WalkWithStatusDTO fromWalk(Walk walk) {
        return new WalkWithStatusDTO(walk.getWalkId(), walk.getWalkStatus());
    }
}
