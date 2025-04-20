package com.example.petoasisbackend.DTO.Comment.AnimalComment;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalMinimumDTO;
import com.example.petoasisbackend.DTO.Comment.Comment.CommentConciseDTO;
import com.example.petoasisbackend.DTO.Comment.Comment.CommentMediumDTO;
import com.example.petoasisbackend.DTO.Comment.Comment.CommentVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Comment.AnimalComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCommentMediumDTO implements ModelDTO<AnimalComment> {
    private AnimalMinimumDTO animal;
    private CommentMediumDTO comment;

    public static AnimalCommentMediumDTO fromAnimalComment(AnimalComment comment) {
        return new AnimalCommentMediumDTO(
                AnimalMinimumDTO.fromAnimal(comment.getAnimal()),
                CommentMediumDTO.fromComment(comment.getComment())
        );
    }
}
