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
public class WalkMinimumDTO implements ModelDTO<Walk> {
    private Long walkId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static WalkMinimumDTO fromWalk(Walk walk) {
        return new WalkMinimumDTO(
                walk.getWalkId(),
                walk.getStartTime(),
                walk.getEndTime()
        );
    }
}
