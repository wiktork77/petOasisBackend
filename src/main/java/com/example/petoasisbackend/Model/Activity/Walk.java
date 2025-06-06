package com.example.petoasisbackend.Model.Activity;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Status.WalkStatus;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Walk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walkId;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal pupil;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person caretaker;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter supervisor;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private WalkStatus walkStatus;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime actualStartTime;

    private LocalDateTime actualEndTime;

    public Walk(Animal pupil, Person caretaker, Shelter supervisor, LocalDateTime startTime, LocalDateTime endTime, WalkStatus status) {
        this.pupil = pupil;
        this.caretaker = caretaker;
        this.supervisor = supervisor;
        this.startTime = startTime;
        this.endTime = endTime;
        this.walkStatus = status;
    }

    public Walk() {}
}
