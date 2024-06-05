package com.example.petoasisbackend.Model.AnimalBreed;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CatBreed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer breedId;

    @Column(length = 48, unique = true, nullable = false)
    private String breedName;
}
