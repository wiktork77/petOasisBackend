package com.example.petoasisbackend.DTO.Activity.Walk;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Status.WalkStatus;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class WalkVerboseDTO implements ModelDTO<Walk> {
    private Long walkId;
    private Animal pupil;
    private Person caretaker;
    private Shelter supervisor;
    private WalkStatus walkStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private WalkVerboseDTO(Long walkId, Animal pupil, Person caretaker, Shelter supervisor, WalkStatus walkStatus, LocalDateTime startTime, LocalDateTime endTime) {
        this.walkId = walkId;
        this.pupil = pupil;
        this.caretaker = caretaker;
        this.supervisor = supervisor;
        this.walkStatus = walkStatus;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static WalkVerboseDTO fromWalk(Walk walk) {
        return new WalkVerboseDTO(
                walk.getWalkId(),
                walk.getPupil(),
                walk.getCaretaker(),
                walk.getSupervisor(),
                walk.getWalkStatus(),
                walk.getStartTime(),
                walk.getEndTime()
        );
    }

}
