package com.example.petoasisbackend.DTO.User.GSU;

import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GSUUpdateDTO {
    private Long systemUserId;

    private String login;

    private String email;

    private String phoneNumber;

    public static GSUUpdateDTO fromGSU(GeneralSystemUser gsu) {
        return new GSUUpdateDTO(
                gsu.getSystemUserId(),
                gsu.getLogin(),
                gsu.getEmail(),
                gsu.getPhoneNumber()
        );
    }
}
