package com.example.petoasisbackend.DTO.Activity.Walk;


import com.example.petoasisbackend.DTO.Animal.Animal.AnimalMediumDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.Status.WalkStatus.WalkStatusNameDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonMediumDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterMediumDTO;
import com.example.petoasisbackend.Model.Activity.Walk;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Status.WalkStatus;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WalkMediumDTO implements ModelDTO<Walk> {
    private Long walkId;

    private AnimalMediumDTO pupil;

    private PersonMediumDTO caretaker;

    private ShelterMediumDTO supervisor;

    private WalkStatusNameDTO walkStatus;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public static WalkMediumDTO fromWalk(Walk walk) {
        return new WalkMediumDTO(
                walk.getWalkId(),
                AnimalMediumDTO.fromAnimal(walk.getPupil()),
                PersonMediumDTO.fromPerson(walk.getCaretaker()),
                ShelterMediumDTO.fromShelter(walk.getSupervisor()),
                WalkStatusNameDTO.fromWalkStatus(walk.getWalkStatus()),
                walk.getStartTime(),
                walk.getEndTime()
        );
    }
}
