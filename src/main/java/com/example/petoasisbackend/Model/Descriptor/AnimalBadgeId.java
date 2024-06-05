package com.example.petoasisbackend.Model.Descriptor;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;


@Data
public class AnimalBadgeId implements Serializable {
    private Long animal;
    private Integer badge;


    public AnimalBadgeId() {
    }

    public AnimalBadgeId(Long animal, Integer badge) {
        this.animal = animal;
        this.badge = badge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalBadgeId that = (AnimalBadgeId) o;
        return Objects.equals(animal, that.animal) && Objects.equals(badge, that.badge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(animal, badge);
    }
}