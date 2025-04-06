package com.example.petoasisbackend.Exception.Person;

public class PersonAlreadyExistsException extends Exception{
    public PersonAlreadyExistsException(String message) {
        super(message);
    }
}
