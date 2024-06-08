package com.example.petoasisbackend.Model.Descriptor;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class AnimalCommentId implements Serializable {
    private Long animal;
    private Long comment;

    public AnimalCommentId() {}

    public AnimalCommentId(Long animal, Long comment) {
        this.animal = animal;
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalCommentId that = (AnimalCommentId) o;
        return Objects.equals(animal, that.animal) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animal, comment);
    }
}
