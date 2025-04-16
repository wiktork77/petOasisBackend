package com.example.petoasisbackend.DTO.Activity.Walk;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Activity.Walk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WalkMediumDTO implements ModelDTO<Walk> {
    private Long walkId;

    public static WalkMediumDTO fromWalk(Walk walk) {
        return new WalkMediumDTO(
                walk.getWalkId()
        );
    }
}
