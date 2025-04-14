package com.example.petoasisbackend.DTO.User.GSU;


import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GSUProfilePictureDTO {
    private Long systemUserId;
    private String pictureUrl;

    public static GSUProfilePictureDTO fromGSU(GeneralSystemUser gsu) {
        return new GSUProfilePictureDTO(
                gsu.getSystemUserId(),
                gsu.getPictureUrl()
        );
    }
}
