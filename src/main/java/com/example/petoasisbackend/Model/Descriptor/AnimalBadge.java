package com.example.petoasisbackend.Model.Descriptor;


import com.example.petoasisbackend.Model.Animal.Animal;
import jakarta.persistence.*;

@Entity
@IdClass(AnimalBadgeId.class)
public class AnimalBadge {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "badge_id")
    private Badge badge;

    // Dodatkowe pola, gettery, settery
}
