package com.example.petoasisbackend.Model.Descriptor;

import lombok.Data;

import java.io.Serializable;


@Data
public class AnimalBadgeId implements Serializable {
    private Long animal;
    private Integer badge;
}