package com.example.petoasisbackend.Model.AnimalBreed;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DogBreed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer breedId;

    @Column(length = 48, unique = true, nullable = false)
    private String breedName;
}
