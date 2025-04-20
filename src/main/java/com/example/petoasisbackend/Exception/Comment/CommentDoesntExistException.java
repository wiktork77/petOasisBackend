package com.example.petoasisbackend.Exception.Comment;

public class CommentDoesntExistException extends Exception {
    public CommentDoesntExistException(String message) {
        super(message);
    }
}
