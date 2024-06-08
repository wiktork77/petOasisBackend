package com.example.petoasisbackend.Request;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Descriptor.Comment;
import lombok.Getter;

@Getter
public class AnimalCommentRequest {
    private Animal animal;
    private Comment comment;
}
