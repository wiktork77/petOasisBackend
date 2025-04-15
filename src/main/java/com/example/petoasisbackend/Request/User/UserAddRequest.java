package com.example.petoasisbackend.Request.User;

import com.example.petoasisbackend.Validation.Password.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserAddRequest {
    @NotBlank(message = "must not be blank")
    private String login;

    @ValidPassword
    @Schema(example = "Password123")
    private String password;

    @NotBlank(message = "must not be blank")
    @Email(message = "must be a valid email")
    @Schema(example = "user@example.com")
    private String email;

    @Schema(example = "http://example.com/picture.jpg")
    private String pictureUrl;

    @NotBlank(message = "must not be blank")
    private String phoneNumber;
}
