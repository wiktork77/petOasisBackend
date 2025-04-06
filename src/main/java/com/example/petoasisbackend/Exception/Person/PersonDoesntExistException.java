package com.example.petoasisbackend.Exception.Person;

public class PersonDoesntExistException extends Exception{
    public PersonDoesntExistException(String message) {
        super(message);
    }
}
