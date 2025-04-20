package com.example.petoasisbackend.Model.Comment;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Users.Person;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
public class AnimalComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long animalCommentId;

    @ManyToOne
    @JoinColumn(name = "animal_id")
    private Animal animal;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public AnimalComment(Animal animal, Comment comment) {
        this.animal = animal;
        this.comment = comment;
    }
}