package com.example.petoasisbackend.DTO.Activity.Walk;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Activity.Walk;
import lombok.Getter;

@Getter
public class WalkMediumDTO implements ModelDTO<Walk> {

    public WalkMediumDTO() {}

    public static WalkMediumDTO fromWalk(Walk walk) {
        return new WalkMediumDTO();
    }
}
