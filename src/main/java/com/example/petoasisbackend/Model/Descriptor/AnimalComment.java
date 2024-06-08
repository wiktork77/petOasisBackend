package com.example.petoasisbackend.Model.Descriptor;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(AnimalCommentId.class)
public class AnimalComment {

    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "animal_id", referencedColumnName = "animalId")
    private Animal animal;

    @Id
    @ManyToOne
    @JoinColumn(name = "comment_id", referencedColumnName = "commentId")
    private Comment comment;


    @Override
    public String toString() {
        return "AnimalComment{" +
                "animal=" + animal +
                ", comment=" + comment +
                '}';
    }
}
