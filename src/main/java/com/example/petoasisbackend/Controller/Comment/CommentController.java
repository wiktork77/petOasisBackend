package com.example.petoasisbackend.Controller.Comment;

import com.example.petoasisbackend.DTO.Comment.Comment.CommentUpdateDTO;
import com.example.petoasisbackend.DTO.Comment.Comment.CommentVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Comment.CommentDoesntExistException;
import com.example.petoasisbackend.Exception.GSU.UserDoesntExistException;
import com.example.petoasisbackend.Model.Comment.Comment;
import com.example.petoasisbackend.Request.Comment.CommentUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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


    @Operation(summary = "Get a comment of arbitrary type with given id and detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a comment", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentVerboseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get comment with id '2292' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getComment(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<Comment> response = commentService.getComment(id, level);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CommentDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all comments posted by an author with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a list of comments", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CommentVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "404", description = "Author not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get comments of the author with id '17' because the author doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/authors/{id}")
    public ResponseEntity<Object> getAuthorComments(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            List<ModelDTO<Comment>> response = commentService.getAuthorComments(id, level);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update the content of a comment with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated a comment", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CommentUpdateDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\n" +
                                                    "  \"content\": \"must not be blank\"\n" +
                                                    "}"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update comment with id '3154' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable Long id, @RequestBody @Valid CommentUpdateRequest request) {
        try {
            CommentUpdateDTO response = commentService.updateComment(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CommentDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete a comment with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted a comment", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete comment with id '41' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (CommentDoesntExistException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
