package com.example.petoasisbackend.DTO.Comment.AnimalComment;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalMinimumDTO;
import com.example.petoasisbackend.DTO.Comment.Comment.CommentConciseDTO;
import com.example.petoasisbackend.DTO.Comment.Comment.CommentMinimumDTO;
import com.example.petoasisbackend.DTO.Comment.Comment.CommentVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Comment.AnimalComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCommentConciseDTO implements ModelDTO<AnimalComment> {
    private AnimalMinimumDTO animal;
    private CommentConciseDTO comment;

    public static AnimalCommentConciseDTO fromAnimalComment(AnimalComment comment) {
        return new AnimalCommentConciseDTO(
                AnimalMinimumDTO.fromAnimal(comment.getAnimal()),
                CommentConciseDTO.fromComment(comment.getComment())
        );
    }
}
