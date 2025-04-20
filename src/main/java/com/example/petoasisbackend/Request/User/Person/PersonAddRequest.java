package com.example.petoasisbackend.Request.User.Person;

import com.example.petoasisbackend.Model.Enum.Gender;
import com.example.petoasisbackend.Request.User.UserAddRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PersonAddRequest extends UserAddRequest {
    @NotBlank(message = "must not be blank")
    private String name;

    @NotBlank(message = "must not be blank")
    private String surname;

    private String address;

    @NotNull(message = "must not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(example = "1990-12-15", type = "string", format = "date")
    private LocalDate birthDate;

    private Gender gender;

}
