package com.example.petoasisbackend.Mapper.Comment;

import com.example.petoasisbackend.DTO.Comment.AnimalComment.AnimalCommentConciseDTO;
import com.example.petoasisbackend.DTO.Comment.AnimalComment.AnimalCommentMediumDTO;
import com.example.petoasisbackend.DTO.Comment.AnimalComment.AnimalCommentMinimumDTO;
import com.example.petoasisbackend.DTO.Comment.AnimalComment.AnimalCommentVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Mapper.DetailLevelMapper;
import com.example.petoasisbackend.Model.Comment.AnimalComment;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AnimalCommentMapper implements DetailLevelMapper<AnimalComment> {
    @Override
    public Function<AnimalComment, ModelDTO<AnimalComment>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return AnimalCommentVerboseDTO::fromAnimalComment;
            }
            case MEDIUM -> {
                return AnimalCommentMediumDTO::fromAnimalComment;
            }
            case CONCISE -> {
                return AnimalCommentConciseDTO::fromAnimalComment;
            }
            case MINIMUM -> {
                return AnimalCommentMinimumDTO::fromAnimalComment;
            }
            default -> {
                return AnimalCommentMinimumDTO::fromAnimalComment;
            }
        }
    }
}
