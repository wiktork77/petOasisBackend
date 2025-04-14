package com.example.petoasisbackend.Request.GSU;


import com.example.petoasisbackend.Validation.Password.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
@Getter
public class PasswordChangeRequest {
    private String oldPassword;

    @ValidPassword
    @Schema(example = "Password123")
    private String newPassword;
}
