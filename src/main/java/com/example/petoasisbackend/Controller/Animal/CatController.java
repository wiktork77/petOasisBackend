package com.example.petoasisbackend.Controller.Animal;

import com.example.petoasisbackend.DTO.Animal.Cat.CatChangeBreedDTO;
import com.example.petoasisbackend.DTO.Animal.Cat.CatMinimumDTO;
import com.example.petoasisbackend.DTO.Animal.Cat.CatUpdateDTO;
import com.example.petoasisbackend.DTO.Animal.Cat.CatVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusDoesntExistException;
import com.example.petoasisbackend.Exception.Breed.Cat.CatBreedDoesntExist;
import com.example.petoasisbackend.Exception.Breed.Dog.DogBreedDoesntExist;
import com.example.petoasisbackend.Exception.Cat.CatDoesntExistException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusDoesntExistException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Model.Animal.Cat;
import com.example.petoasisbackend.Request.Animal.Cat.CatAddRequest;
import com.example.petoasisbackend.Request.Animal.Cat.CatChangeBreedRequest;
import com.example.petoasisbackend.Request.Animal.Cat.CatUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.CatService;
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
@RequestMapping("/cats")
public class CatController {
    @Autowired
    private CatService catService;


    @Operation(summary = "Get all cats with given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a list of all cats", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CatVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<List<ModelDTO<Cat>>> get(@RequestParam DataDetailLevel level) {
        List<ModelDTO<Cat>> cats = catService.get(level);
        return new ResponseEntity<>(cats, HttpStatus.OK);
    }


    @Operation(summary = "Get a cat with given id and given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a cat", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CatVerboseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Cat not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get cat with id '5' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<Cat> cat = catService.getById(id, level);
            return new ResponseEntity<>(cat, HttpStatus.OK);
        } catch (CatDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Add a new cat")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully created a cat", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CatMinimumDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid request data",
                                            value = "{\n" +
                                                    "  \"shelterId\": \"must not be null\",\n" +
                                                    "  \"name\": \"must not be blank\",\n" +
                                                    "  \"height\": \"must not be null\",\n" +
                                                    "  \"length\": \"must not be null\",\n" +
                                                    "  \"weight\": \"must not be null\",\n" +
                                                    "  \"isDeclawed\": \"must not be null\",\n" +
                                                    "  \"vocalizationLevel\": \"must not be null\",\n" +
                                                    "  \"gender\": \"must not be null\",\n" +
                                                    "  \"enjoysPetting\": \"must not be null\",\n" +
                                                    "  \"description\": \"must not be blank\",\n" +
                                                    "  \"cat\": \"must not be null\",\n" +
                                                    "  \"healthStatusId\": \"must not be null\",\n" +
                                                    "  \"availabilityStatusId\": \"must not be null\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Data doesn't meet requirements",
                                            value = "{\n" +
                                                    "  \"height\": \"must be greater than 0\",\n" +
                                                    "  \"length\": \"must be greater than 0\",\n" +
                                                    "  \"weight\": \"must be greater than 0\",\n" +
                                                    "  \"vocalizationLevel\": \"must be greater than or equal to 0\"\n" +
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
                    @ApiResponse(responseCode = "404", description = "One of id components doesn't exist", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "Shelter doesn't exist",
                                            value = "Cannot add a cat because shelter with id '63' doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Cat breed doesn't exist",
                                            value = "Cannot add a cat because cat breed with id '9' doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Health status doesn't exist",
                                            value = "Cannot add a cat because health status with id '921' doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Availability status doesn't exist",
                                            value = "Cannot add a cat because availability status with id '128' doesn't exist"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody @Valid CatAddRequest request) {
        try {
            CatMinimumDTO response = catService.add(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (HealthStatusDoesntExistException | DogBreedDoesntExist | ShelterDoesntExistException |
                 AvailabilityStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update an existing cat")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CatUpdateDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid request data",
                                            value = "{\n" +
                                                    "  \"isDeclawed\": \"must not be null\",\n" +
                                                    "  \"vocalizationLevel\": \"must not be null\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Data doesn't meet requirements",
                                            value = "{\n" +
                                                    "  \"vocalizationLevel\": \"must be greater than or equal to 0\"\n" +
                                                    "}"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Cat not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update cat with id '73' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid CatUpdateRequest request) {
        try {
            CatUpdateDTO response = catService.update(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CatDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Change the breed of an existing cat")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CatChangeBreedDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\n" +
                                                    "  \"breedName\": \"must not be blank\"\n" +
                                                    "}"

                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Cat or breed not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "Cat not found",
                                            value = "Cannot change breed of cat with id '552' because it doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Cat breed not found",
                                            value = "Cannot change breed of cat with id '24' to 'Kitty' because that breed doesn't exist"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PatchMapping("/{id}/breed")
    public ResponseEntity<Object> updateBreed(@PathVariable Long id, @RequestBody @Valid CatChangeBreedRequest request) {
        try {
            CatChangeBreedDTO response = catService.changeBreed(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CatDoesntExistException | CatBreedDoesntExist e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete an existing cat with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "400", description = "Cat couldn't be deleted because it would violate data integrity", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete cat because it is referenced by something else"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Cat not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete cat with id '881' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)

            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            catService.delete(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Cannot delete cat because it is referenced by something else", HttpStatus.BAD_REQUEST);
        } catch (CatDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
