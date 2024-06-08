package com.example.petoasisbackend.Service;


import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Descriptor.AnimalComment;
import com.example.petoasisbackend.Model.Descriptor.AnimalCommentId;
import com.example.petoasisbackend.Model.Descriptor.Comment;
import com.example.petoasisbackend.Repository.AnimalCommentRepository;
import com.example.petoasisbackend.Repository.AnimalRepository;
import com.example.petoasisbackend.Repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private AnimalCommentRepository animalCommentRepository;
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    public Comment addComment(Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

    public AnimalComment addAnimalComment(Long animalId, Comment comment) {
        if (!animalRepository.existsById(animalId)) {
            throw new IllegalArgumentException("Animal with id " + animalId  + " doesnt exist");
        }
        Animal animal = animalRepository.getReferenceById(animalId);
        AnimalComment animalComment = new AnimalComment();
        animalComment.setAnimal(animal);
        animalComment.setComment(comment);
        animalCommentRepository.save(animalComment);
        return animalComment;
    }

    public AnimalComment removeAnimalComment(Long commentId, Long animalId) {
        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("Comment with id " + commentId  + " doesnt exist");
        }
        if (!animalRepository.existsById(animalId)) {
            throw new IllegalArgumentException("Animal with id " + animalId  + " doesnt exist");
        }
        Comment comment = commentRepository.getReferenceById(commentId);
        Animal animal = animalRepository.getReferenceById(animalId);
        if (!animalCommentRepository.existsByAnimalAndComment(animal, comment)) {
            throw new IllegalArgumentException("Cannot remove comment that doesn't exist!");
        }
        AnimalCommentId id = new AnimalCommentId(animalId, commentId);
        AnimalComment comment1 = animalCommentRepository.getReferenceById(id);
        animalCommentRepository.delete(comment1);
        return comment1;
    }

    public Comment deleteComment(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("Comment with id " + commentId  + " doesnt exist");
        }
        Comment comment = commentRepository.getReferenceById(commentId);
        commentRepository.delete(comment);
        return comment;
    }

    public Comment updateComment(Long commentId, Comment other) {
        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("Comment with id " + commentId  + " doesnt exist");
        }
        Comment comment = commentRepository.getReferenceById(commentId);
        comment.inheritFromOther(other);
        commentRepository.save(comment);
        return comment;
    }
}
