package com.example.petoasisbackend.DTO.Activity.Walk;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.Status.WalkStatus.WalkStatusNameDTO;
import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Model.Status.WalkStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalkWithStatusDTO implements ModelDTO<Walk> {
    private Long walkId;
    private WalkStatusNameDTO status;

    public static WalkWithStatusDTO fromWalk(Walk walk) {
        return new WalkWithStatusDTO(
                walk.getWalkId(),
                WalkStatusNameDTO.fromWalkStatus(walk.getWalkStatus())
        );
    }
}
