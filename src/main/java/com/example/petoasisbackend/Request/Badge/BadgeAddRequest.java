package com.example.petoasisbackend.Request.Badge;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BadgeAddRequest {
    @NotBlank(message = "must not be blank")
    private String badgeName;
}

