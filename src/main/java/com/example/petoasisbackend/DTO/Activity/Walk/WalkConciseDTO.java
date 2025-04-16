package com.example.petoasisbackend.DTO.Activity.Walk;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Activity.Walk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalkConciseDTO implements ModelDTO<Walk> {
    private Long walkId;

    public static WalkConciseDTO fromWalk(Walk walk) {
        return new WalkConciseDTO(
                walk.getWalkId()
        );
    }
}
