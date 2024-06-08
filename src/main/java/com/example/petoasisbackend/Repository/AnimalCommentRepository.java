package com.example.petoasisbackend.Repository;

import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Descriptor.AnimalComment;
import com.example.petoasisbackend.Model.Descriptor.AnimalCommentId;
import com.example.petoasisbackend.Model.Descriptor.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimalCommentRepository extends JpaRepository<AnimalComment, AnimalCommentId> {
    boolean existsByAnimalAndComment(Animal animal, Comment comment);
}
