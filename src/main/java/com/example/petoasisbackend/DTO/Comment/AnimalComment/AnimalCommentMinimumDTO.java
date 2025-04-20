package com.example.petoasisbackend.DTO.Comment.AnimalComment;

import com.example.petoasisbackend.DTO.Animal.Animal.AnimalMinimumDTO;
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
public class AnimalCommentMinimumDTO implements ModelDTO<AnimalComment> {
    private AnimalMinimumDTO animal;
    private CommentMinimumDTO comment;

    public static AnimalCommentMinimumDTO fromAnimalComment(AnimalComment comment) {
        return new AnimalCommentMinimumDTO(
                AnimalMinimumDTO.fromAnimal(comment.getAnimal()),
                CommentMinimumDTO.fromComment(comment.getComment())
        );
    }
}
