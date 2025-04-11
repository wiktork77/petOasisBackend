package com.example.petoasisbackend.DTO.Activity.Walk;

import com.example.petoasisbackend.Model.Activity.Walk;
import lombok.Getter;

@Getter
public class WalkMinimumDTO {
    private Long walkId;

    private WalkMinimumDTO(Long walkId) {
        this.walkId = walkId;
    }

    public WalkMinimumDTO() {}

    public static WalkMinimumDTO fromWalk(Walk walk) {
        return new WalkMinimumDTO(walk.getWalkId());
    }
}
