package com.example.petoasisbackend.Model.Activity;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Descriptor.WalkStatus;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

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

    private LocalDate startTime;

    private LocalDate endTime;
}
