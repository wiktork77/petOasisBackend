package com.example.petoasisbackend.DTO.Activity.Walk;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.Status.WalkStatus.WalkStatusVerboseDTO;
import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Status.WalkStatus;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalkVerboseDTO implements ModelDTO<Walk> {
    private Long walkId;
    private Animal pupil;
    private Person caretaker;
    private Shelter supervisor;
    private WalkStatusVerboseDTO walkStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static WalkVerboseDTO fromWalk(Walk walk) {
        return new WalkVerboseDTO(
                walk.getWalkId(),
                walk.getPupil(),
                walk.getCaretaker(),
                walk.getSupervisor(),
                WalkStatusVerboseDTO.fromWalkStatus(walk.getWalkStatus()),
                walk.getStartTime(),
                walk.getEndTime()
        );
    }

}
