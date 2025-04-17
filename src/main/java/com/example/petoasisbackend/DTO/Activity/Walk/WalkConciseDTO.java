package com.example.petoasisbackend.DTO.Activity.Walk;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalConciseDTO;
import com.example.petoasisbackend.DTO.Animal.Animal.AnimalMediumDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.Status.WalkStatus.WalkStatusNameDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonConciseDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonMinimumDTO;
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
    private AnimalMediumDTO pupil;
    private PersonMinimumDTO caretaker;
    private WalkStatusNameDTO walkStatus;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public static WalkConciseDTO fromWalk(Walk walk) {
        return new WalkConciseDTO(
                walk.getWalkId(),
                AnimalMediumDTO.fromAnimal(walk.getPupil()),
                PersonMinimumDTO.fromPerson(walk.getCaretaker()),
                WalkStatusNameDTO.fromWalkStatus(walk.getWalkStatus()),
                walk.getStartTime(),
                walk.getEndTime()
        );
    }
}
