package com.example.petoasisbackend.Controller.Users;

import com.example.petoasisbackend.DTO.User.Shelter.ShelterMinimumDTO;
import com.example.petoasisbackend.DTO.User.Shelter.ShelterRegisterDTO;
import com.example.petoasisbackend.Exception.Shelter.ShelterAlreadyExistsException;
import com.example.petoasisbackend.Exception.Shelter.ShelterDoesntExistException;
import com.example.petoasisbackend.Exception.Shelter.ShelterInvalidRequestException;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Request.DataDetailLevel;
import com.example.petoasisbackend.Service.ShelterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shelter")
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
    @GetMapping("/")
    public ResponseEntity<Object> getAll(@RequestParam DataDetailLevel level) {
        Object shelters = shelterService.getShelters(level);
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
    public Object getById(@PathVariable Long id, @RequestParam DataDetailLevel level) {
        try {
            Object shelter = shelterService.getShelterById(id, level);
            return new ResponseEntity<>(shelter, HttpStatus.OK);
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
    public Object getByName(@PathVariable String name, @RequestParam DataDetailLevel level) {
        try {
            Object shelter = shelterService.getShelterByName(name, level);
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
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot add shelter because the request is invalid"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "409", description = "", content = @Content(
                            examples = {
                                    @ExampleObject(
                                            value = "Cannot add shelter because given login already exists"
                                    )
                            }
                    )),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @PostMapping("/add")
    public ResponseEntity<Object> add(@RequestBody ShelterRegisterDTO shelterRegisterDTO) {
        try {
            ShelterMinimumDTO response = shelterService.addShelter(shelterRegisterDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ShelterInvalidRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ShelterAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @Operation(summary = "Delete a shelter user with given if from the system")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        // TODO: complete
        return null;
    }

}
