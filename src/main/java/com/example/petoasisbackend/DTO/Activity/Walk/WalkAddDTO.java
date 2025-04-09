package com.example.petoasisbackend.DTO.Activity.Walk;

import com.example.petoasisbackend.Model.Activity.Walk;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class WalkAddDTO {
    private Long animalId;

    private Long personId;

    private Long shelterId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public WalkAddDTO(Long animalId, Long personId, Long shelterId, LocalDateTime startTime, LocalDateTime endTime) {
        this.animalId = animalId;
        this.personId = personId;
        this.shelterId = shelterId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static WalkAddDTO fromWalk(Walk walk) {
        return new WalkAddDTO(
                walk.getPupil().getAnimalId(),
                walk.getCaretaker().getPersonId(),
                walk.getSupervisor().getShelterId(),
                walk.getStartTime(),
                walk.getEndTime()
        );
    }
}
