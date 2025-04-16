package com.example.petoasisbackend.Controller.Animal;

import com.example.petoasisbackend.DTO.Animal.Dog.DogChangeBreedDTO;
import com.example.petoasisbackend.DTO.Animal.Dog.DogMinimumDTO;
import com.example.petoasisbackend.DTO.Animal.Dog.DogUpdateDTO;
import com.example.petoasisbackend.DTO.Animal.Dog.DogVerboseDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedMinimumDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.AvailabilityStatus.AvailabilityStatusDoesntExistException;
import com.example.petoasisbackend.Exception.Breed.Cat.CatBreedDoesntExist;
import com.example.petoasisbackend.Exception.Breed.Dog.DogBreedDoesntExist;
import com.example.petoasisbackend.Exception.Dog.DogDoesntExistException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusDoesntExistException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Model.Animal.Dog;
import com.example.petoasisbackend.Request.Animal.Dog.DogAddRequest;
import com.example.petoasisbackend.Request.Animal.Dog.DogChangeBreedRequest;
import com.example.petoasisbackend.Request.Animal.Dog.DogUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.DogService;
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
import java.util.Objects;

@RestController
@RequestMapping("/dog")
public class DogController {
    @Autowired
    private DogService dogService;

    @Operation(summary = "Get all dogs with given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a list of all dogs", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DogVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/")
    public ResponseEntity<Object> getAll(@RequestParam DataDetailLevel level) {
        List<ModelDTO<Dog>> dogs = dogService.getDogs(level);
        return new ResponseEntity<>(dogs, HttpStatus.OK);
    }

    @Operation(summary = "Get a dog with given id and given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a dog", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DogVerboseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Dog not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get dog with id '5' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<Dog> dog = dogService.getDogById(id, level);
            return new ResponseEntity<>(dog, HttpStatus.OK);
        } catch (DogDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NO_CONTENT);
        }
    }



    @Operation(summary = "Add a new dog")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully created a dog", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DogMinimumDTO.class)
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
                                                    "  \"isMuzzleRequired\": \"must not be null\",\n" +
                                                    "  \"barkingLevel\": \"must not be null\",\n" +
                                                    "  \"gender\": \"must not be null\",\n" +
                                                    "  \"enjoysPetting\": \"must not be null\",\n" +
                                                    "  \"description\": \"must not be blank\",\n" +
                                                    "  \"dogBreedId\": \"must not be null\",\n" +
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
                                                    "  \"barkingLevel\": \"must be greater than or equal to 0\"\n" +
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
                                            value = "Cannot add a dog because shelter with id '63' doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Dog breed doesn't exist",
                                            value = "Cannot add a dog because dog breed with id '9' doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Health status doesn't exist",
                                            value = "Cannot add a dog because health status with id '921' doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Availability status doesn't exist",
                                            value = "Cannot add a dog because availability status with id '128' doesn't exist"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody @Valid DogAddRequest request) {
        try {
            DogMinimumDTO response = dogService.addDog(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (HealthStatusDoesntExistException | ShelterDoesntExistException | DogBreedDoesntExist |
                 AvailabilityStatusDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update an existing dog")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DogUpdateDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid request data",
                                            value = "{\n" +
                                                    "  \"isMuzzleRequired\": \"must not be null\",\n" +
                                                    "  \"barkingLevel\": \"must not be null\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Data doesn't meet requirements",
                                            value = "{\n" +
                                                    "  \"barkingLevel\": \"must be greater than or equal to 0\"\n" +
                                                    "}"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Dog not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update dog with id '73' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid DogUpdateRequest request) {
        try {
            DogUpdateDTO response = dogService.update(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DogDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }



    @Operation(summary = "Change the breed of an existing dog")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DogChangeBreedDTO.class)
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
                    @ApiResponse(responseCode = "404", description = "Dog or breed not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "Dog not found",
                                            value = "Cannot change breed of dog with id '552' because it doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Cat breed not found",
                                            value = "Cannot change breed of dog with id '24' to 'Doggo' because that breed doesn't exist"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PatchMapping("/{id}/breed")
    public ResponseEntity<Object> updateBreed(@PathVariable Long id, @RequestBody @Valid DogChangeBreedRequest request) {
        try {
            DogChangeBreedDTO response = dogService.changeBreed(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DogDoesntExistException | CatBreedDoesntExist e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Delete an existing dog")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Successfully deleted", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "404", description = "Dog not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete dog with id '881' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)

            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            dogService.delete(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (DogDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
