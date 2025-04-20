package com.example.petoasisbackend.Request.User.GSU;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ProfilePictureChangeRequest {
    @NotBlank(message = "must not be blank")
    private String url;
}
