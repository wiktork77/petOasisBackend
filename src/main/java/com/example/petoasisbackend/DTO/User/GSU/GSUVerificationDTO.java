package com.example.petoasisbackend.DTO.User.GSU;


import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GSUVerificationDTO implements ModelDTO<GeneralSystemUser> {
    private Long systemUserId;
    private boolean isVerified;

    private GSUVerificationDTO(Long systemUserId, boolean isVerified) {
        this.systemUserId = systemUserId;
        this.isVerified = isVerified;
    }

    public static GSUVerificationDTO fromGSU(GeneralSystemUser gsu) {
        return new GSUVerificationDTO(
                gsu.getSystemUserId(),
                gsu.getIsVerified()
        );
    }
}
