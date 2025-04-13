package com.example.petoasisbackend.Request.Person;

import com.example.petoasisbackend.Model.Users.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PersonAddRequest {
    @NotBlank(message = "must not be blank")
    private String login;

    @NotBlank(message = "must not be blank")
    private String password;

    @NotBlank(message = "must not be blank")
    @Email(message = "must be a valid email")
    @Schema(example = "person@example.com", description = "Valid email address")
    private String email;

    @NotBlank(message = "must not be blank")
    private String phoneNumber;

    @Schema(example = "http://example.com/picture.jpg")
    private String pictureUrl;

    @NotBlank(message = "must not be blank")
    private String name;

    private String address;

    @NotBlank(message = "must not be blank")
    private String surname;

    @NotNull(message = "must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(example = "1990-12-15", type = "string", format = "date")
    private LocalDate birthDate;

    private Gender gender;

}
