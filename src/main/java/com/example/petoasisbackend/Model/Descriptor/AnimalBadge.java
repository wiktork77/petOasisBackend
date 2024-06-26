package com.example.petoasisbackend.Model.Descriptor;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(AnimalBadgeId.class)
public class AnimalBadge {
    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "animal_id", referencedColumnName = "animalId")
    private Animal animal;

    @Id
    @ManyToOne
    @JoinColumn(name = "badge_id", referencedColumnName = "badgeId")
    private Badge badge;
}
