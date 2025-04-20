package com.example.petoasisbackend.Controller.Descriptor;


import com.example.petoasisbackend.DTO.Badge.Badge.BadgeMinimumDTO;
import com.example.petoasisbackend.DTO.Badge.Badge.BadgeUpdateDTO;
import com.example.petoasisbackend.DTO.Badge.Badge.BadgeVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Badge.BadgeAlreadyExists;
import com.example.petoasisbackend.Exception.Badge.BadgeDoesntExistException;
import com.example.petoasisbackend.Model.Badge.Badge;
import com.example.petoasisbackend.Request.Badge.BadgeAddRequest;
import com.example.petoasisbackend.Request.Badge.BadgeUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.BadgeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/badges")
public class BadgeController {
    @Autowired
    private BadgeService badgeService;


    @Operation(summary = "Get badges with given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a list of all badges", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BadgeVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<Object> get(@RequestParam DataDetailLevel level) {
        List<ModelDTO<Badge>> badges = badgeService.getAll(level);
        return new ResponseEntity<>(badges, HttpStatus.OK);
    }

    @Operation(summary = "Get a badge with given id and detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a badge", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BadgeVerboseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Badge not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get badge with id '821' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<Badge> badge = badgeService.getById(id, level);
            return new ResponseEntity<>(badge, HttpStatus.OK);
        } catch (BadgeDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get a badge with given name and detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a badge", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BadgeVerboseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Badge not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get badge with name 'bdg' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getByName(@PathVariable String name, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<Badge> badge = badgeService.getByName(name, level);
            return new ResponseEntity<>(badge, HttpStatus.OK);
        } catch (BadgeDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Add a new badge")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully added a badge", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BadgeMinimumDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\n" +
                                                    "  \"badgeName\": \"must not be blank\"\n" +
                                                    "}"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Badge already exists", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot add badge with name 'Good boy' because it already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PostMapping
    public ResponseEntity<Object> add(@RequestBody @Valid BadgeAddRequest request) {
        try {
            BadgeMinimumDTO response = badgeService.add(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (BadgeAlreadyExists e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Delete a badge with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted a badge", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "400", description = "Badge couldn't be deleted because it would violate data integrity", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete badge because it is referenced by something else"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Badge not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete badge with id '14' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        try {
            badgeService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Cannot delete badge because it is referenced by something else", HttpStatus.BAD_REQUEST);
        } catch (BadgeDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update a badge with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully updated a badge", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BadgeUpdateDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\n" +
                                                    "  \"badgeName\": \"must not be blank\"\n" +
                                                    "}"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Badge not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update badge with id '42' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Badge already exists", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update badge because badge with name 'Good boy' already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody @Valid BadgeUpdateRequest request) {
        try {
            BadgeUpdateDTO response = badgeService.update(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadgeDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (BadgeAlreadyExists e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


}
