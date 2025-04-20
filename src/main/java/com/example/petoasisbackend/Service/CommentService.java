package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.Comment.Comment.CommentUpdateDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Comment.CommentDoesntExistException;
import com.example.petoasisbackend.Exception.GSU.UserDoesntExistException;
import com.example.petoasisbackend.Mapper.Comment.CommentMapper;
import com.example.petoasisbackend.Model.Comment.Comment;
import com.example.petoasisbackend.Repository.AnimalCommentRepository;
import com.example.petoasisbackend.Repository.CommentRepository;
import com.example.petoasisbackend.Repository.SystemUserRepository;
import com.example.petoasisbackend.Request.Comment.CommentUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private AnimalCommentRepository animalCommentRepository;

    public ModelDTO<Comment> getComment(Long id, DataDetailLevel level) throws CommentDoesntExistException {
        if (!commentRepository.existsById(id)) {
            throw new CommentDoesntExistException("Cannot get comment with id '" + id + "' because it doesn't exist");
        }

        Comment comment = commentRepository.findById(id).get();
        var mapper = commentMapper.getMapper(level);

        return mapper.apply(comment);
    }

    public List<ModelDTO<Comment>> getAuthorComments(Long id, DataDetailLevel level) throws UserDoesntExistException {
        if (!systemUserRepository.existsById(id)) {
            throw new UserDoesntExistException("Cannot get comments of the author with id '" + id + "' because the author doesn't exist");
        }

        List<Comment> comments = commentRepository.findCommentsByAuthor_SystemUserId(id);
        var mapper = commentMapper.getMapper(level);

        return comments.stream().map(mapper).collect(Collectors.toList());
    }

    public CommentUpdateDTO updateComment(Long id, CommentUpdateRequest request) throws CommentDoesntExistException {
        if (!commentRepository.existsById(id)) {
            throw new CommentDoesntExistException("Cannot update comment with id '" + id + "' because it doesn't exist");
        }

        Comment comment = commentRepository.findById(id).get();

        comment.update(request);
        commentRepository.save(comment);

        return CommentUpdateDTO.fromComment(comment);
    }

    @Transactional
    public void deleteComment(Long id) throws CommentDoesntExistException {
        if (!commentRepository.existsById(id)) {
            throw new CommentDoesntExistException("Cannot delete comment with id '" + id + "' because it doesn't exist");
        }

        Comment comment = commentRepository.findById(id).get();

        switch (comment.getType()) {
            case ANIMAL -> {
                animalCommentRepository.deleteById(comment.getParentId());
            }
        }

    }
}
