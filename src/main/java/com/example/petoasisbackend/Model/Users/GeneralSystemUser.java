package com.example.petoasisbackend.Model.Users;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class GeneralSystemUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long systemUserId;

    @Column(nullable = false)
    private Boolean isVerified;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(length = 128)
    private String pictureUrl;
}
