package com.example.petoasisbackend.Request.User.Shelter;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Users.Shelter;
import com.example.petoasisbackend.Request.User.UserAddRequest;
import com.example.petoasisbackend.Validation.Password.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Getter
@NoArgsConstructor
@ToString
public class ShelterAddRequest extends UserAddRequest {

    @NotBlank(message = "must not be blank")
    private String name;

    @NotBlank(message = "must not be blank")
    private String address;

    private String website;

}
