package com.example.petoasisbackend.DTO.User.GSU;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GSUVerboseDTO implements ModelDTO<GeneralSystemUser> {
    private Long systemUserId;
    private String login;
    private Boolean isVerified;
    private String phoneNumber;
    private String pictureUrl;
    private String type;
    private Long parentId;

    public GSUVerboseDTO(Long systemUserId, String login, Boolean isVerified, String phoneNumber, String pictureUrl, String type, Long parentId) {
        this.systemUserId = systemUserId;
        this.login = login;
        this.isVerified = isVerified;
        this.phoneNumber = phoneNumber;
        this.pictureUrl = pictureUrl;
        this.type = type;
        this.parentId = parentId;
    }

    public static GSUVerboseDTO fromGSU(GeneralSystemUser gsu) {
        return new GSUVerboseDTO(
                gsu.getSystemUserId(),
                gsu.getLogin(),
                gsu.getIsVerified(),
                gsu.getPhoneNumber(),
                gsu.getPictureUrl(),
                gsu.getType(),
                gsu.getParentId()
        );
    }
}

