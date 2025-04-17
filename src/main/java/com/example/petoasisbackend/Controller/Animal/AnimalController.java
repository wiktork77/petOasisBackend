package com.example.petoasisbackend.Controller.Animal;


import com.example.petoasisbackend.DTO.Animal.Animal.*;
import com.example.petoasisbackend.DTO.Animal.Cat.CatVerboseDTO;
import com.example.petoasisbackend.DTO.Animal.Dog.DogVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Exception.HealthStatus.HealthStatusDoesntExistException;
import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Request.Animal.AnimalUpdateRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Request.Status.AvailabilityStatus.AvailabilityStatusUpdateRequest;
import com.example.petoasisbackend.Request.Status.HealthStatus.HealthStatusUpdateRequest;
import com.example.petoasisbackend.Service.AnimalService;
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

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/animal")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @Operation(summary = "Get animals with given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned list of animals", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AnimalVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/")
    public ResponseEntity<Object> get(@RequestParam DataDetailLevel level) {
        List<ModelDTO<Animal>> animals = animalService.get(level);
        return new ResponseEntity<>(animals, HttpStatus.OK);
    }

    @Operation(summary = "Get an animal with given id and detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned an animal", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnimalVerboseDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get animal with id '23' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            ModelDTO<Animal> animal = animalService.getById(id, level);
            return new ResponseEntity<>(animal, HttpStatus.OK);
        } catch (AnimalDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all dogs and cats with given detail level", description = "Returns both dog and cat instances instead of just animal instances")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned list of cats and dogs", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    oneOf = {
                                            DogVerboseDTO.class,
                                            CatVerboseDTO.class
                                    }
                            )
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/mixed")
    public ResponseEntity<Object> getMixed(@RequestParam DataDetailLevel level) {
        List<Object> animals = animalService.getMixed(level);
        return new ResponseEntity<>(animals, HttpStatus.OK);
    }

    @Operation(summary = "Update the picture of an animal with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnimalPictureChangeDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot change picture of animal with id '12' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PatchMapping("/{id}/picture/{url}")
    public ResponseEntity<Object> changePicture(@PathVariable Long id, @PathVariable String url) {
        try {
            AnimalPictureChangeDTO response = animalService.changePicture(id, url);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AnimalDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @Operation(summary = "Update the health status of an animal with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnimalHealthStatusChangeDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{ \"healthStatus\": \"must not be blank\" }"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Animal or status not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "Animal not found",
                                            value = "Cannot change health status of animal with id '5' because it doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Status not found",
                                            value = "Cannot change health status of animal with id '5' to 'Unhlthy' because the status doesn't exist"

                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PatchMapping("/{id}/status/health")
    public ResponseEntity<Object> changeHealthStatus(@PathVariable Long id, @RequestBody @Valid HealthStatusUpdateRequest request) {
        try {
            AnimalHealthStatusChangeDTO response = animalService.changeHealthStatus(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HealthStatusDoesntExistException | AnimalDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update the availability status of an animal with given id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnimalAvailabilityStatusChangeDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{ \"availability\": \"must not be blank\" }"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Animal or status not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "Animal not found",
                                            value = "Cannot change health status of animal with id '5' because it doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Status not found",
                                            value = "Cannot change health status of animal with id '5' to 'Not avlable' because the status doesn't exist"

                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PatchMapping("/{id}/status/availability")
    public ResponseEntity<Object> changeAvailabilityStatus(@PathVariable Long id, @RequestBody @Valid AvailabilityStatusUpdateRequest request) {
        try {
            AnimalAvailabilityStatusChangeDTO response = animalService.changeAvailabilityStatus(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (HealthStatusDoesntExistException | AnimalDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Update animal related data")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully updated", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnimalUpdateDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            name = "Invalid request data",
                                            value = "{\n" +
                                                    "  \"name\": \"must not be blank\",\n" +
                                                    "  \"weight\": \"must not be null\",\n" +
                                                    "  \"height\": \"must not be null\",\n" +
                                                    "  \"length\": \"must not be null\",\n" +
                                                    "  \"gender\": \"must not be null\",\n" +
                                                    "  \"enjoysPetting\": \"must not be null\",\n" +
                                                    "  \"description\": \"must not be blank\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Invalid data format",
                                            value = "{\n" +
                                                    "  \"gender\": \"must be one of: M, F, U\",\n" +
                                                    "  \"birthDate\": \"must be in format yyyy-MM-dd\"\n" +
                                                    "}"
                                    ),
                                    @ExampleObject(
                                            name = "Data doesn't meet requirements",
                                            value = "{\n" +
                                                    "  \"height\": \"must be greater than 0\",\n" +
                                                    "  \"length\": \"must be greater than 0\",\n" +
                                                    "  \"weight\": \"must be greater than 0\"\n" +
                                                    "}"
                                    ),
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot update the animal with id '78' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody @Valid AnimalUpdateRequest request) {
        try {
            AnimalUpdateDTO response = animalService.update(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AnimalDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
