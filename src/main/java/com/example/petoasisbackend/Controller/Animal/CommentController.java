package com.example.petoasisbackend.Controller.Animal;


import com.example.petoasisbackend.Model.Descriptor.AnimalComment;
import com.example.petoasisbackend.Model.Descriptor.Comment;
import com.example.petoasisbackend.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/getAll")
    public List<Comment> getAll() {
        return commentService.getComments();
    }

    @PostMapping("/add")
    public ResponseEntity<String> add(@RequestBody Comment comment) {
        try {
            Comment commentResult = commentService.addComment(comment);
            return new ResponseEntity<>("Comment with id " + commentResult.getCommentId() + " added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/animals/add/{id}")
    public ResponseEntity<String> add(@PathVariable Long id, @RequestBody Comment comment) {
        try {
            Comment comment1 = commentService.addComment(comment);
            AnimalComment animalComment =  commentService.addAnimalComment(id, comment1);
            return new ResponseEntity<>("Animal commented successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/animals/delete/{commentId}/{animalId}")
    public ResponseEntity<String> delete(@PathVariable Long commentId, @PathVariable Long animalId) {
        try {
            commentService.removeAnimalComment(commentId, animalId);
            return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody Comment other) {
        try {
            commentService.updateComment(id, other);
            return new ResponseEntity<>("Comment updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
