package com.example.petoasisbackend.Model.Descriptor;

import java.io.Serializable;
import java.util.Objects;

public class FavoriteAnimalsId implements Serializable {
    private Long person;
    private Long animal;

    public FavoriteAnimalsId() {}

    public FavoriteAnimalsId(Long person, Long animal) {
        this.person = person;
        this.animal = animal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoriteAnimalsId that = (FavoriteAnimalsId) o;
        return Objects.equals(person, that.person) && Objects.equals(animal, that.animal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, animal);
    }
}
