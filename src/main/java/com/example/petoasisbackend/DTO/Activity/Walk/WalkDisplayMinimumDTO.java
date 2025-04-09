package com.example.petoasisbackend.DTO.Activity.Walk;

import com.example.petoasisbackend.Model.Activity.Walk;
import lombok.Getter;

@Getter
public class WalkDisplayMinimumDTO {
    private Long walkId;

    public WalkDisplayMinimumDTO(Long walkId) {
        this.walkId = walkId;
    }

    public static WalkDisplayMinimumDTO fromWalk(Walk walk) {
        return new WalkDisplayMinimumDTO(walk.getWalkId());
    }
}
