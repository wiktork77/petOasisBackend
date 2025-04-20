package com.example.petoasisbackend.Service;

import com.example.petoasisbackend.DTO.Comment.AnimalComment.AnimalCommentMinimumDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Exception.GSU.UserDoesntExistException;
import com.example.petoasisbackend.Mapper.Comment.AnimalCommentMapper;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Comment.AnimalComment;
import com.example.petoasisbackend.Model.Comment.Comment;
import com.example.petoasisbackend.Model.Enum.CommentType;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Repository.AnimalCommentRepository;
import com.example.petoasisbackend.Repository.AnimalRepository;
import com.example.petoasisbackend.Repository.CommentRepository;
import com.example.petoasisbackend.Repository.SystemUserRepository;
import com.example.petoasisbackend.Request.Comment.AnimalComment.AnimalCommentPostRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalCommentService {
    @Autowired
    private AnimalCommentRepository animalCommentRepository;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private SystemUserRepository systemUserRepository;

    @Autowired
    private AnimalCommentMapper animalCommentMapper;
    @Autowired
    private CommentRepository commentRepository;

    public List<ModelDTO<AnimalComment>> getAnimalComments(Long id, DataDetailLevel level) throws AnimalDoesntExistException {
        if (!animalRepository.existsById(id)) {
            throw new AnimalDoesntExistException("Cannot get comments of an animal with id '" + id + "' because the animal doesnt exist");
        }

        List<AnimalComment> comments = animalCommentRepository.getAnimalCommentsByAnimal_AnimalId(id);
        var mapper = animalCommentMapper.getMapper(level);

        return comments.stream().map(mapper).collect(Collectors.toList());
    }

    public int getAnimalCommentCount(Long id) throws AnimalDoesntExistException {
        if (!animalRepository.existsById(id)) {
            throw new AnimalDoesntExistException("Cannot get comment count of an animal with id '" + id + "' because the animal doesnt exist");
        }

        List<AnimalComment> comments = animalCommentRepository.getAnimalCommentsByAnimal_AnimalId(id);

        return comments.size();
    }

    @Transactional
    public AnimalCommentMinimumDTO postAnimalComment(AnimalCommentPostRequest request) throws AnimalDoesntExistException, UserDoesntExistException {
        if (!animalRepository.existsById(request.getAnimalId())) {
            throw new AnimalDoesntExistException("Cannot post comment for an animal with id '" + request.getAnimalId() + "' because the animal doesnt exist");
        }

        if (!systemUserRepository.existsById(request.getSystemUserId())) {
            throw new UserDoesntExistException("User with id '" + request.getSystemUserId() + "' cannot post comment because it doesn't exist");
        }

        GeneralSystemUser author = systemUserRepository.findById(request.getSystemUserId()).get();
        Animal animal = animalRepository.findById(request.getAnimalId()).get();

        Comment comment = new Comment(
                author,
                request.getContent(),
                LocalDateTime.now(),
                CommentType.ANIMAL
        );
        Comment savedComment = commentRepository.save(comment);

        AnimalComment animalComment = new AnimalComment(animal, savedComment);
        AnimalComment savedAnimalComment = animalCommentRepository.save(animalComment);

        comment.setParentId(savedAnimalComment.getAnimalCommentId());

        commentRepository.save(comment);

        return AnimalCommentMinimumDTO.fromAnimalComment(savedAnimalComment);
    }
}

