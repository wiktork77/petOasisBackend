package com.example.petoasisbackend.Controller.AnimalBreed;

import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedMinimumDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedUpdateDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.Status.HealthStatus.HealthStatusMinimumDTO;
import com.example.petoasisbackend.Exception.Breed.Dog.DogBreedAlreadyExists;
import com.example.petoasisbackend.Exception.Breed.Dog.DogBreedDoesntExist;
import com.example.petoasisbackend.Model.AnimalBreed.DogBreed;
import com.example.petoasisbackend.Model.Status.HealthStatus;
import com.example.petoasisbackend.Request.AnimalBreed.Dog.DogBreedAddRequest;
import com.example.petoasisbackend.Request.AnimalBreed.Dog.DogBreedUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.DogBreedService;
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
@RequestMapping("/breeds/dogs")
public class DogBreedController {
    @Autowired
    private DogBreedService dogBreedService;

    @Operation(summary = "Get all dog breeds with given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned list of all dog breeds", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DogBreedVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam DataDetailLevel level) {
        List<ModelDTO<DogBreed>> breeds = dogBreedService.getAll(level);
        return new ResponseEntity<>(breeds, HttpStatus.OK);
    }


    @Operation(summary = "Get dog breed with given id and given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned dog breed", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DogBreedVerboseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Dog breed not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get dog breed with id '5' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<DogBreed> breed = dogBreedService.getById(id, level);
            return new ResponseEntity<>(breed, HttpStatus.OK);
        } catch (DogBreedDoesntExist e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get health status with given name")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned dog breed", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = DogBreedVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "404", description = "Dog breed not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get dog breed with name 'Husgy' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getByName(@PathVariable String name, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<DogBreed> breed = dogBreedService.getByName(name, level);
            return new ResponseEntity<>(breed, HttpStatus.OK);
        } catch (DogBreedDoesntExist e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Add a new dog breed")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully created a dog breed", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DogBreedMinimumDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{ \"breedName\": \"must not be blank\" }"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Dog breed with given name already exists", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot add dog breed 'Husky' because it already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody @Valid DogBreedAddRequest request) {
        try {
            DogBreedMinimumDTO response = dogBreedService.add(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DogBreedAlreadyExists e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Update an existing dog breed")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated a dog breed", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = DogBreedUpdateDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete update request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{ \"breedName\": \"must not be blank\" }"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Dog breed not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update dog breed with id '72' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Dog breed with given name already exists", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update dog breed with id '4' to 'Husky' because 'Husky' already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody @Valid DogBreedUpdateRequest request) {
        try {
            DogBreedUpdateDTO response = dogBreedService.update(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DogBreedDoesntExist e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DogBreedAlreadyExists e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Delete an existing dog breed")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Dog breed successfully deleted", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "400", description = "Dog breed couldn't be deleted because it would violate data integrity", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete dog breed because it is still referenced by a dog"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Dog breed not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete Dog breed with id '64' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        try {
            dogBreedService.delete(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Cannot delete dog breed because it is still referenced by a dog", HttpStatus.BAD_REQUEST);
        } catch (DogBreedDoesntExist e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
