package com.example.petoasisbackend.Controller.User;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterMinimumDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterUpdateDTO;
import com.example.petoasisbackend.Exception.GSU.UserAlreadyExistsException;
import com.example.petoasisbackend.Exception.Shelter.ShelterAlreadyExistsException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.User.Shelter.ShelterAddRequest;
import com.example.petoasisbackend.Request.User.Shelter.ShelterUpdateRequest;
import com.example.petoasisbackend.Service.ShelterService;
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
@RequestMapping("/shelters")
public class ShelterController {
    @Autowired
    private ShelterService shelterService;

    @Operation(summary = "Get all shelters with given data details")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a list of all shelters", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Shelter.class))
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam DataDetailLevel level) {
        List<ModelDTO<Shelter>> shelters = shelterService.getShelters(level);
        return new ResponseEntity<>(shelters, HttpStatus.OK);
    }

    @Operation(summary = "Get a shelter with given id and data details")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned shelter", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Shelter.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Shelter not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get shelter with id '217' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<Shelter> shelter = shelterService.getShelterById(id, level);
            return new ResponseEntity<>(shelter, HttpStatus.OK);
        } catch (ShelterDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get animals to belong to given shelter")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned animals", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Shelter.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Shelter not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get shelter animals with id '217' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}/animals")
    public ResponseEntity<Object> getAnimals(@PathVariable Long id) {
        try {
            List<Object> response = shelterService.getAnimals(id);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ShelterDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get a shelter with given name and data details")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned shelter", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Shelter.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Shelter not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get shelter with name 'super shelter' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getByName(@PathVariable String name, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<Shelter> shelter = shelterService.getShelterByName(name, level);
            return new ResponseEntity<>(shelter, HttpStatus.OK);
        } catch (ShelterDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a shelter user to the system")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Shelter successfully created", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShelterMinimumDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete add request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\n" +
                                                    "  \"login\": \"must not be blank\",\n" +
                                                    "  \"password\": \"must not be blank\",\n" +
                                                    "  \"phoneNumber\": \"must not be blank\",\n" +
                                                    "  \"name\": \"must not be blank\",\n" +
                                                    "  \"address\": \"must not be blank\",\n" +
                                                    "  \"email\": \"must be a valid email\"\n" +
                                                    "}"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Data causing uniqueness conflict", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name =  "Shelter with that name already exists",
                                            value = "Cannot add shelter because shelter with 'Good shelter' name already exists"
                                    ),
                                    @ExampleObject(
                                            name = "User with that login already exists",
                                            value = "Cannot add shelter because given login already exists"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody @Valid ShelterAddRequest request) {
        try {
            ShelterMinimumDTO response = shelterService.addShelter(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ShelterAlreadyExistsException | UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update shelter related data")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated a shelter", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ShelterUpdateDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Shelter not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update shelter with id '732' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Shelter name collision", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update shelter with id '32' because shelter with name 'Best dog shelter' already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid ShelterUpdateRequest request) {
        try {
            ShelterUpdateDTO response = shelterService.updateShelter(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ShelterDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (ShelterAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Delete a shelter user with given id from the system")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted a shelter", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "400", description = "Shelter couldn't be deleted because it would violate data integrity", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete shelter because it is still referenced"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Shelter not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete shelter with id '21' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            shelterService.deleteShelterById(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Cannot delete shelter because it is still referenced", HttpStatus.BAD_REQUEST);
        } catch (ShelterDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
