package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.Comment.AnimalComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalCommentRepository extends JpaRepository<AnimalComment, Long> {
    List<AnimalComment> getAnimalCommentsByAnimal_AnimalId(Long animalId);

    void deleteAnimalCommentByComment_CommentId(Long commentId);
}
