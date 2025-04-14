package com.example.petoasisbackend.DTO.User.GSU;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GSUMediumDTO implements ModelDTO<GeneralSystemUser> {
    private Long systemUserId;

    public static GSUMediumDTO fromGSU(GeneralSystemUser gsu) {
        return new GSUMediumDTO(
                gsu.getSystemUserId()
        );
    }
}
