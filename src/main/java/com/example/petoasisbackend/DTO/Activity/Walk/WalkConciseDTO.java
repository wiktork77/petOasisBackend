package com.example.petoasisbackend.DTO.Activity.Walk;

import com.example.petoasisbackend.Model.Activity.Walk;
import lombok.Getter;

@Getter
public class WalkConciseDTO {

    public WalkConciseDTO() {}

    public static WalkConciseDTO fromWalk(Walk walk) {
        return new WalkConciseDTO();
    }
}
