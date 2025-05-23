package com.example.petoasisbackend.Model.Badge;


import com.example.petoasisbackend.Model.Animal.Animal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(AnimalBadgeId.class)
public class AnimalBadge {
    @Id
    @ManyToOne
    @JoinColumn(name = "animal_id", referencedColumnName = "animalId")
    private Animal animal;

    @Id
    @ManyToOne
    @JoinColumn(name = "badge_id", referencedColumnName = "badgeId")
    private Badge badge;
}
