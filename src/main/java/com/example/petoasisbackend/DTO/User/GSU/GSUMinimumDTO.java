package com.example.petoasisbackend.DTO.User.GSU;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GSUMinimumDTO implements ModelDTO<GeneralSystemUser> {
    private Long systemUserId;

    private GSUMinimumDTO(Long systemUserId) {
        this.systemUserId = systemUserId;
    }

    public static GSUMinimumDTO fromGSU(GeneralSystemUser gsu) {
        return new GSUMinimumDTO(
                gsu.getSystemUserId()
        );
    }

}
