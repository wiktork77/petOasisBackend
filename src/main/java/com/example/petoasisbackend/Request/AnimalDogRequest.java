package com.example.petoasisbackend.Request;

import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Animal.Dog;
import lombok.Getter;


@Getter
public class AnimalDogRequest {
    private Animal animal;
    private Dog dog;
}
