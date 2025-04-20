package com.example.petoasisbackend.Controller.Comment;

import com.example.petoasisbackend.DTO.Comment.AnimalComment.AnimalCommentMinimumDTO;
import com.example.petoasisbackend.DTO.Comment.AnimalComment.AnimalCommentVerboseDTO;
import com.example.petoasisbackend.DTO.Comment.Comment.CommentVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Exception.GSU.UserDoesntExistException;
import com.example.petoasisbackend.Model.Comment.AnimalComment;
import com.example.petoasisbackend.Request.Comment.AnimalComment.AnimalCommentPostRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.AnimalCommentService;
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
@RequestMapping("/comments/animals")
public class AnimalCommentController {
    @Autowired
    private AnimalCommentService animalCommentService;

    @Operation(summary = "Get all comments associated with the animal with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a list of comments", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AnimalCommentVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get comments of an animal with id '422' because the animal doesnt exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAnimalComments(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            List<ModelDTO<AnimalComment>> response = animalCommentService.getAnimalComments(id, level);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AnimalDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all comment count associated with the animal with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned the number of comments", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(type = "integer", example = "15")
                    )),
                    @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get comment count of an animal with id '321' because the animal doesnt exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}/count")
    public ResponseEntity<Object> getAnimalCommentCount(@PathVariable Long id) {
        try {
            Integer response = animalCommentService.getAnimalCommentCount(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AnimalDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Post a comment about an animal")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully posted a comment", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnimalCommentMinimumDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\n" +
                                                    "  \"systemUserId\": \"must not be null\",\n" +
                                                    "  \"animalId\": \"must not be null\",\n" +
                                                    "  \"content\": \"must not be blank\"\n" +
                                                    "}"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Animal or user not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "Animal not found",
                                            value = "Cannot post comment for an animal with id '82' because the animal doesnt exist"
                                    ),
                                    @ExampleObject(
                                            name = "User not found",
                                            value = "User with id '91' cannot post comment because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<Object> postAnimalComment(@RequestBody @Valid AnimalCommentPostRequest request) {
        try {
            AnimalCommentMinimumDTO response = animalCommentService.postAnimalComment(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (AnimalDoesntExistException | UserDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
