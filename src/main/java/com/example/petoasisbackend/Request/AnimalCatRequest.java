package com.example.petoasisbackend.Request;

import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Cat;
import lombok.Getter;

@Getter
public class AnimalCatRequest {
    private Animal animal;
    private Cat cat;
}
