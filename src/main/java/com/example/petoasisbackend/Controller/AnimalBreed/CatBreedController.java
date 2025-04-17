package com.example.petoasisbackend.Controller.AnimalBreed;

import com.example.petoasisbackend.DTO.AnimalBreed.Cat.CatBreedMinimumDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Cat.CatBreedUpdateDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Cat.CatBreedVerboseDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedMinimumDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedUpdateDTO;
import com.example.petoasisbackend.DTO.AnimalBreed.Dog.DogBreedVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Breed.Cat.CatBreedAlreadyExists;
import com.example.petoasisbackend.Exception.Breed.Cat.CatBreedDoesntExist;
import com.example.petoasisbackend.Model.AnimalBreed.CatBreed;
import com.example.petoasisbackend.Request.AnimalBreed.Cat.CatBreedAddRequest;
import com.example.petoasisbackend.Request.AnimalBreed.Cat.CatBreedUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.CatBreedService;
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
@RequestMapping("/breeds/cats")
public class CatBreedController {
    @Autowired
    private CatBreedService catBreedService;


    @Operation(summary = "Get all cat breeds with given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned list of all cat breeds", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CatBreedVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<Object> getAll(@RequestParam DataDetailLevel level) {
        List<ModelDTO<CatBreed>> breeds = catBreedService.getAll(level);
        return new ResponseEntity<>(breeds, HttpStatus.OK);
    }

    @Operation(summary = "Get cat breed with given id and given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned cat breed", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CatBreedVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "404", description = "Cat breed not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get cat breed with id '5' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Integer id, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<CatBreed> breed = catBreedService.getById(id, level);
            return new ResponseEntity<>(breed, HttpStatus.OK);
        } catch (CatBreedDoesntExist e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Get health status with given name")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned cat breed", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CatBreedVerboseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Cat breed not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get cat breed with name 'Kity' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getByName(@PathVariable String name, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<CatBreed> breed = catBreedService.getByName(name, level);
            return new ResponseEntity<>(breed, HttpStatus.OK);
        } catch (CatBreedDoesntExist e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Add a new cat breed")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully created a cat breed", content = @Content(
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
                    @ApiResponse(responseCode = "409", description = "Cat breed with given name already exists", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot add cat breed 'kiti' because it already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody @Valid CatBreedAddRequest request) {
        try {
            CatBreedMinimumDTO response = catBreedService.add(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (CatBreedAlreadyExists e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Update an existing dog breed")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated a cat breed", content = @Content(
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
                    @ApiResponse(responseCode = "404", description = "Cat breed not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update cat breed with id '72' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Cat breed with given name already exists", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update cat breed with id '4' to 'Husky' because 'kiti' already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Integer id, @RequestBody @Valid CatBreedUpdateRequest request) {
        try {
            CatBreedUpdateDTO response = catBreedService.update(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CatBreedDoesntExist e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (CatBreedAlreadyExists e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


    @Operation(summary = "Delete an existing dog breed")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Cat breed successfully deleted", content = @Content(
                            mediaType = "text/plain"
                    )),
                    @ApiResponse(responseCode = "400", description = "Cat breed couldn't be deleted because it would violate data integrity", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete cat breed because it is still referenced by a cat"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Cat breed not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot delete cat breed with id '64' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content),
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Integer id) {
        try {
            catBreedService.delete(id);
            return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>("Cannot delete cat breed because it is still referenced by a cat", HttpStatus.BAD_REQUEST);
        } catch (CatBreedDoesntExist e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
