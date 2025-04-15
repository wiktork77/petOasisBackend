package com.example.petoasisbackend.Model.Animal;


import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Cat implements Walkable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catId;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Column(nullable = false)
    private Boolean isDeclawed;

    @Column(nullable = false)
    private Byte vocalizationLevel;

    @Column(length = 48)
    private String favoriteTreat;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private CatBreed catBreed;

    public void inheritFromOtherCat(Cat other) {
        if (other.animal != null)
            this.animal.inheritFromOtherAnimal(other.animal);

        if (other.isDeclawed != null)
            this.isDeclawed = other.isDeclawed;

        if (other.vocalizationLevel != null)
            this.vocalizationLevel = other.vocalizationLevel;

        this.favoriteTreat = other.favoriteTreat;

        if (other.catBreed != null)
            this.catBreed = other.catBreed;
    }
}
