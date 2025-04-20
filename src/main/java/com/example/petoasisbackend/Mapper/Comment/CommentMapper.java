package com.example.petoasisbackend.Mapper.Comment;

import com.example.petoasisbackend.DTO.Comment.Comment.CommentConciseDTO;
import com.example.petoasisbackend.DTO.Comment.Comment.CommentMediumDTO;
import com.example.petoasisbackend.DTO.Comment.Comment.CommentMinimumDTO;
import com.example.petoasisbackend.DTO.Comment.Comment.CommentVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Mapper.DetailLevelMapper;
import com.example.petoasisbackend.Model.Comment.Comment;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CommentMapper implements DetailLevelMapper<Comment> {
    @Override
    public Function<Comment, ModelDTO<Comment>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return CommentVerboseDTO::fromComment;
            }
            case MEDIUM -> {
                return CommentMediumDTO::fromComment;
            }
            case CONCISE -> {
                return CommentConciseDTO::fromComment;
            }
            case MINIMUM -> {
                return CommentMinimumDTO::fromComment;
            }
            default -> {
                return CommentMinimumDTO::fromComment;
            }
        }
    }
}
