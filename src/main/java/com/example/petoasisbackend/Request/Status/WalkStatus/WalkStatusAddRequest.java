package com.example.petoasisbackend.Request.Status.WalkStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class WalkStatusAddRequest {
    @NotBlank(message = "must not be blank")
    private String status;
}
