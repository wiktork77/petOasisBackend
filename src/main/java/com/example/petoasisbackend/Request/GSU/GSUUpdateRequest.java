package com.example.petoasisbackend.Request.GSU;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GSUUpdateRequest {
    @NotBlank(message = "must not be blank")
    private String login;

    @NotBlank(message = "must not be blank")
    private String email;

    @NotBlank(message = "must not be blank")
    private String phoneNumber;
}
