package com.example.petoasisbackend.Model.Users;


import com.example.petoasisbackend.Request.User.GSU.GSUUpdateRequest;
import com.example.petoasisbackend.Request.User.Person.PersonAddRequest;
import com.example.petoasisbackend.Request.User.Shelter.ShelterAddRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
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

    @Column(length = 64, nullable = false)
    private String email;

    @Column(length = 128)
    private String pictureUrl;

    private AccountType type;

    private Long parentId;

    public void update(GSUUpdateRequest request) {
        this.login = request.getLogin();
        this.email = request.getEmail();
        this.phoneNumber = request.getPhoneNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralSystemUser that = (GeneralSystemUser) o;
        return Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

    public GeneralSystemUser(String login, Boolean isVerified, String phoneNumber, String email, String pictureUrl, AccountType type, Long parentId) {
        this.login = login;
        this.isVerified = isVerified;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.pictureUrl = pictureUrl;
        this.type = type;
        this.parentId = parentId;
    }

    public static GeneralSystemUser fromShelterAddRequest(ShelterAddRequest request) {
        return new GeneralSystemUser(
                request.getLogin(),
                false,
                request.getPhoneNumber(),
                request.getEmail(),
                request.getPictureUrl(),
                AccountType.SHELTER,
                null
        );
    }

    public static GeneralSystemUser fromPersonAddRequest(PersonAddRequest request) {
        return new GeneralSystemUser(
                request.getLogin(),
                false,
                request.getPhoneNumber(),
                request.getEmail(),
                request.getPictureUrl(),
                AccountType.PERSON,
                null
        );
    }

}
