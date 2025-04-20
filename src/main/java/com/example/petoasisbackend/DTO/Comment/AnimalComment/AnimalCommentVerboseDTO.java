package com.example.petoasisbackend.DTO.Comment.AnimalComment;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalMinimumDTO;
import com.example.petoasisbackend.DTO.Comment.Comment.CommentConciseDTO;
import com.example.petoasisbackend.DTO.Comment.Comment.CommentVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Comment.AnimalComment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnimalCommentVerboseDTO implements ModelDTO<AnimalComment> {
    private AnimalMinimumDTO animal;
    private CommentVerboseDTO comment;

    public static AnimalCommentVerboseDTO fromAnimalComment(AnimalComment comment) {
        return new AnimalCommentVerboseDTO(
                AnimalMinimumDTO.fromAnimal(comment.getAnimal()),
                CommentVerboseDTO.fromComment(comment.getComment())
        );
    }
}
