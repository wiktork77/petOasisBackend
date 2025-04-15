package com.example.petoasisbackend.Request.User.GSU;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GSUUpdateRequest {
    @NotBlank(message = "must not be blank")
    private String login;

    @NotBlank(message = "must not be blank")
    @Email(message = "must be a valid email")
    private String email;

    @NotBlank(message = "must not be blank")
    private String phoneNumber;
}
