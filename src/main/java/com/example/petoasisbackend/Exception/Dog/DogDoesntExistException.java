package com.example.petoasisbackend.Exception.Dog;

public class DogDoesntExistException extends Exception {
    public DogDoesntExistException(String message) {
        super(message);
    }
}
