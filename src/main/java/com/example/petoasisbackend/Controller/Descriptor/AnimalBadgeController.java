package com.example.petoasisbackend.Controller.Descriptor;

import com.example.petoasisbackend.DTO.Badge.AnimalBadge.AnimalBadgeMinimumDTO;
import com.example.petoasisbackend.DTO.Badge.AnimalBadge.AnimalBadgeVerboseDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Exception.Animal.AnimalDoesntExistException;
import com.example.petoasisbackend.Exception.AnimalBadge.AnimalBadgeAlreadyAttachedException;
import com.example.petoasisbackend.Exception.AnimalBadge.AnimalBadgeNotAttachedException;
import com.example.petoasisbackend.Exception.Badge.BadgeDoesntExistException;
import com.example.petoasisbackend.Model.Badge.AnimalBadge;
import com.example.petoasisbackend.Request.AnimalBadge.AnimalBadgeAttachRequest;
import com.example.petoasisbackend.Request.AnimalBadge.AnimalBadgeDetachRequest;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.AnimalBadgeService;
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
@RequestMapping("/badges/animals")
public class AnimalBadgeController {

    @Autowired
    private AnimalBadgeService animalBadgeService;

    @Operation(summary = "Get all instances of animal badges with given detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a list of all animal attached badges", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AnimalBadgeVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping
    public ResponseEntity<Object> getBadgesAttachedToAnimals(@RequestParam DataDetailLevel level) {
        List<ModelDTO<AnimalBadge>> response = animalBadgeService.getBadgesAttachedToAnimals(level);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(summary = "Get all badges of an animal with given id and detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a list of animal's badges", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AnimalBadgeVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "404", description = "Animal not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get badges of the animal with id '12' because it doesn't exist."
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getBadgesOfAnAnimal(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            List<ModelDTO<AnimalBadge>> response = animalBadgeService.getBadgesOfAnAnimal(id, level);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AnimalDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get all recipients of a badge with given id and detail level")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned a list of badge's recipients", content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = AnimalBadgeVerboseDTO.class))
                    )),
                    @ApiResponse(responseCode = "404", description = "Badge not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot get recipients of badge with id '74' because it doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @GetMapping("/badge/{id}")
    public ResponseEntity<Object> getAnimalsWithBadge(@PathVariable Integer id, @RequestParam DataDetailLevel level) {
        try {
            List<ModelDTO<AnimalBadge>> response = animalBadgeService.getAnimalsWithBadge(id, level);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (BadgeDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Attach a badge to an animal")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Successfully attached", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AnimalBadgeMinimumDTO.class)
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete attach request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\n" +
                                                    "  \"animalId\": \"must not be null\",\n" +
                                                    "  \"badgeId\": \"must not be null\"\n" +
                                                    "}"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Animal or badge not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "Animal not found",
                                            value = "Cannot attach a badge to an animal because the animal with id '91' doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Badge not found",
                                            value = "Cannot attach a badge to an animal because the badge with id '1142' doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Badge already attached", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot attach a badge to an animal because the badge is already attached"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @PostMapping("/attach")
    public ResponseEntity<Object> attachBadge(@RequestBody @Valid AnimalBadgeAttachRequest request) {
        try {
            AnimalBadgeMinimumDTO response = animalBadgeService.attachBadge(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (BadgeDoesntExistException | AnimalDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AnimalBadgeAlreadyAttachedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Detach a badge from an animal")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Successfully detached", content = @Content(
                            mediaType = "application/json"
                    )),
                    @ApiResponse(responseCode = "400", description = "Invalid or incomplete attach request", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "{\n" +
                                                    "  \"animalId\": \"must not be null\",\n" +
                                                    "  \"badgeId\": \"must not be null\"\n" +
                                                    "}"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "404", description = "Animal or badge not found", content = @Content(
                            mediaType = "text/plain",
                            examples = {
                                    @ExampleObject(
                                            name = "Animal not found",
                                            value = "Cannot detach a badge from an animal because the animal with id '22' doesn't exist"
                                    ),
                                    @ExampleObject(
                                            name = "Badge not found",
                                            value = "Cannot detach a badge from an animal because the badge with id '112' doesn't exist"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "Badge is not attached", content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot detach a badge from an animal because the badge isn't attached"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Server couldn't parse the request", content = @Content)
            }
    )
    @DeleteMapping("/detach")
    public ResponseEntity<Object> detachBadge(@RequestBody @Valid AnimalBadgeDetachRequest request) {
        try {
            animalBadgeService.detachBadge(request);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (BadgeDoesntExistException | AnimalDoesntExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (AnimalBadgeNotAttachedException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

}
