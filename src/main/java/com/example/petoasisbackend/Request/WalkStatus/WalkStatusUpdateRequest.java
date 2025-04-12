package com.example.petoasisbackend.Request.WalkStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class WalkStatusUpdateRequest {
    @NotBlank(message = "must not be blank")
    private String status;
}
