package com.example.petoasisbackend.Model.Users;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GeneralSystemUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long systemUserId;

    @Column(length = 48, nullable = false, unique = true)
    private String login;

    @Column(length = 128, nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean isVerified;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(length = 128)
    private String pictureUrl;

    private String type;

    private Long parentId;

    public void inheritFromOtherGeneralSystemUser(GeneralSystemUser other) {
        if (other.login != null) {
            this.login = other.login;
        }
        if (other.isVerified != null) {
            this.isVerified = other.isVerified;
        }
        if (other.phoneNumber != null) {
            this.phoneNumber = other.phoneNumber;
        }
        this.pictureUrl = other.pictureUrl;
    }
}
