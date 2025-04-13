package com.example.petoasisbackend.Request.Shelter;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Users.Shelter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Getter
@NoArgsConstructor
@ToString
public class ShelterAddRequest {
    @NotBlank(message = "must not be blank")
    private String login;

    @NotBlank(message = "must not be blank")
    private String password;

    @NotBlank(message = "must not be blank")
    @Email(message = "must be a valid email")
    @Schema(example = "shelter@example.com", description = "Valid email address")
    private String email;

    @NotBlank(message = "must not be blank")
    private String phoneNumber;

    @Schema(example = "http://example.com/picture.jpg")
    private String pictureUrl;

    @NotBlank(message = "must not be blank")
    private String name;

    @NotBlank(message = "must not be blank")
    private String address;

    private String website;

}
