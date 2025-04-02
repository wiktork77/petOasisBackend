package com.example.petoasisbackend.Model.Descriptor;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Users.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(FavoriteAnimalsId.class)
public class FavoriteAnimals {
    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "person_id", referencedColumnName = "personId")
    private Person person;

    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "animal_id", referencedColumnName = "animalId")
    private Animal animal;
}
