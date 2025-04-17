package com.example.petoasisbackend.Controller.User;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.*;
import com.example.petoasisbackend.Exception.GSU.UserAlreadyExistsException;
import com.example.petoasisbackend.Exception.GSU.UserDoesntExistException;
import com.example.petoasisbackend.Exception.GSU.UserPasswordDoesntMatch;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.User.GSU.GSUUpdateRequest;
import com.example.petoasisbackend.Request.User.GSU.PasswordChangeRequest;
import com.example.petoasisbackend.Service.GSUService;
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
@RequestMapping("/gsu")
public class GSUController {
    @Autowired
    private GSUService gsuService;

    @Operation(summary = "Get all users with given data details")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a list of all users", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GSUVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam DataDetailLevel level) {
        List<ModelDTO<GeneralSystemUser>> users = gsuService.getAll(level);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Get a user with given id and data details")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned the user", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(oneOf = {
                                    GSUVerboseDTO.class,
                                    GSUMediumDTO.class,
                                    GSUConciseDTO.class,
                                    GSUMinimumDTO.class
                            })
                    )),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get user with id '7' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getGSUById(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<GeneralSystemUser> user = gsuService.getById(id, level);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Verify a user with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully verified the user", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GSUVerificationDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot verify user with id '92' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content),
            }
    )
    @PatchMapping("/{id}/verify")
    public ResponseEntity<Object> verifyUser(@PathVariable Long id) {
        try {
            GSUVerificationDTO user = gsuService.verify(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update the profile picture of a user with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GSUProfilePictureDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Person not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot change profile picture of user with id '114' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content),
            }
    )
    @PatchMapping("/{id}/profile-picture/{url}")
    public ResponseEntity<Object> uploadProfilePicture(@PathVariable Long id, @PathVariable String url) {
        try {
            GSUProfilePictureDTO user = gsuService.changeProfilePicture(id, url);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update user related data")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GSUUpdateDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete add request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid request data",
                                            value = "{\n" +
                                                    "  \"login\": \"must not be blank\",\n" +
                                                    "  \"email\": \"must not be blank\",\n" +
                                                    "  \"phoneNumber\": \"must not be blank\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid email",
                                            value = "{\n" +
                                                    "  \"email\": \"must be a valid email\"\n" +
                                                    "}"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update user with id '22' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "User login collision", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update user because given login already exists"
                                    )
                            }

                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid GSUUpdateRequest request) {
        try {
            GSUUpdateDTO user = gsuService.update(id, request);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (UserDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Change the password of a user with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully changed password", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GSUMinimumDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete add request", content = @Content(
                            examples = {
                                    @ExampleObject(
                                            name = "Passwords dont match",
                                            value = "Current password and given password don't match"
                                    ),
                                    @ExampleObject(
                                            name = "New password doesn't match standard",
                                            value = "{\n" +
                                                    "  \"newPassword\": \"must be at least 8 characters long, must contain at least one uppercase letter, one lowercase letter, and one digit\"\n" +
                                                    "}"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "User not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot change password of user with id '45' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content),
            }
    )
    @PatchMapping("/{id}/password/")
    public ResponseEntity<Object> changePassword(@PathVariable Long id, @RequestBody @Valid PasswordChangeRequest request) {
        try {
            GSUMinimumDTO response = gsuService.changePassword(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UserPasswordDoesntMatch e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (UserDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
