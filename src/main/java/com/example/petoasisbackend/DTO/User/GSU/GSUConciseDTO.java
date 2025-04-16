package com.example.petoasisbackend.DTO.User.GSU;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GSUConciseDTO implements ModelDTO<GeneralSystemUser> {
    private Long systemUserId;

    private String pictureUrl;

    public static GSUConciseDTO fromGSU(GeneralSystemUser gsu) {
        return new GSUConciseDTO(
                gsu.getSystemUserId(),
                gsu.getPictureUrl()
        );
    }
}
