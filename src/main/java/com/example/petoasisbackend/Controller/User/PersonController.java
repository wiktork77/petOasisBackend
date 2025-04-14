package com.example.petoasisbackend.Controller.User;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonMinimumDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonUpdateDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonVerboseDTO;
import com.example.petoasisbackend.Exception.GSU.UserAlreadyExistsException;
import com.example.petoasisbackend.Exception.Person.PersonDoesntExistException;
import com.example.petoasisbackend.Exception.Shelter.ShelterAlreadyExistsException;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.GSUPersonRequest;
import com.example.petoasisbackend.Request.Person.PersonAddRequest;
import com.example.petoasisbackend.Request.Person.PersonUpdateRequest;
import com.example.petoasisbackend.Service.PersonService;
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
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;

    @Operation(summary = "Get all people with given data details")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a list of all shelters", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PersonVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/")
    public ResponseEntity<Object> getAll(@RequestParam DataDetailLevel level) {
        List<ModelDTO<Person>> people = personService.getPeople(level);
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    @Operation(summary = "Get a person with given id and data details")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned person", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PersonVerboseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Person not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get person with id '217' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<Person> person = personService.getPersonById(id, level);
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (PersonDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a person user to the system")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully added", content = @Content(
                            mediaType = "application/json",
                            contentSchema = @Schema(implementation = PersonMinimumDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete add request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid request data",
                                            value = "{\n" +
                                                    "  \"login\": \"must not be blank\",\n" +
                                                    "  \"password\": \"must not be blank\",\n" +
                                                    "  \"phoneNumber\": \"must not be blank\",\n" +
                                                    "  \"name\": \"must not be blank\",\n" +
                                                    "  \"surname\": \"must not be blank\",\n" +
                                                    "  \"address\": \"must not be blank\",\n" +
                                                    "  \"email\": \"must be a valid email\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid data format",
                                            value = "{\n" +
                                                    "  \"gender\": \"must be one of: M, F, U\",\n" +
                                                    "  \"birthDate\": \"must be in format yyyy-MM-dd\"\n" +
                                                    "}"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Data causing uniqueness conflict", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot add user because given login already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody @Valid PersonAddRequest request) {
        try {
            PersonMinimumDTO response = personService.addPerson(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Update person related data")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PersonUpdateDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete add request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid request data",
                                            value = "{\n" +
                                                    "  \"name\": \"must not be blank\",\n" +
                                                    "  \"surname\": \"must not be blank\",\n" +
                                                    "  \"birthDate\": \"must not be null\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid data format",
                                            value = "{\n" +
                                                    "  \"gender\": \"must be one of: M, F, U\",\n" +
                                                    "  \"birthDate\": \"must be in format yyyy-MM-dd\"\n" +
                                                    "}"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Person not found", content = @Content(
                            mediaType = "text/plain",
                            examples = @ExampleObject(
                                    value = "Cannot update person with id '992' because it doesn't exist"
                            )
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid PersonUpdateRequest request) {
        try {
            PersonUpdateDTO response = personService.updatePerson(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (PersonDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Delete person with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "400", description = "Person couldn't be deleted because it would violate data integrity", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete person because it is still referenced"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Person not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete person with id '82' because it doesn't exist"
                                    )
                            }

                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            personService.deletePerson(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Cannot delete person because it is still referenced", HttpStatus.BAD_REQUEST);
        } catch (PersonDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}

