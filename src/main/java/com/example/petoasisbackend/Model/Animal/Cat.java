package com.example.petoasisbackend.Model.Animal;


import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Request.Animal.Cat.CatAddRequest;
import com.example.petoasisbackend.Request.Animal.Cat.CatUpdateRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long catId;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @Column(nullable = false)
    private Boolean isDeclawed;

    @Column(nullable = false)
    private Integer vocalizationLevel;

    @Column(length = 48)
    private String favoriteTreat;

    @ManyToOne
    @JoinColumn(name = "breed_id")
    private CatBreed catBreed;

    public void update(CatUpdateRequest request) {
        this.isDeclawed = request.getIsDeclawed();
        this.vocalizationLevel = request.getVocalizationLevel();
        this.favoriteTreat = request.getFavoriteTreat();
    }

    public Cat(Animal animal, Boolean isDeclawed, Integer vocalizationLevel, String favoriteTreat, CatBreed catBreed) {
        this.animal = animal;
        this.isDeclawed = isDeclawed;
        this.vocalizationLevel = vocalizationLevel;
        this.favoriteTreat = favoriteTreat;
        this.catBreed = catBreed;
    }

    public static Cat fromCatAddRequest(CatAddRequest request) {
        return new Cat(
                null,
                request.getIsDeclawed(),
                request.getVocalizationLevel(),
                request.getFavoriteTreat(),
                null
        );
    }
}
