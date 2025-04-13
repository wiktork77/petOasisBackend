package com.example.petoasisbackend.DTO.Activity.Walk;

import com.example.petoasisbackend.Model.Activity.Walk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WalkWithTimeDTO {
    private Long walkId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static WalkWithTimeDTO fromWalk(Walk walk) {
        return new WalkWithTimeDTO(
                walk.getWalkId(),
                walk.getStartTime(),
                walk.getEndTime()
        );
    }
}
