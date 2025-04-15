package com.example.petoasisbackend.Request.User.Shelter;


import com.example.petoasisbackend.Model.Users.Shelter;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShelterUpdateRequest {
    @NotBlank(message = "must not be blank")
    private String name;

    @NotBlank(message = "must not be blank")
    private String address;

    private String website;
}
