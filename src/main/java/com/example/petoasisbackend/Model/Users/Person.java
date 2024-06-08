package com.example.petoasisbackend.Model.Users;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "system_user_id")
    private GeneralSystemUser generalSystemUser;

    @Column(length = 48, nullable = false, unique = true)
    private String login;

    @Column(length = 128, nullable = false)
    private String password;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(length = 48, nullable = false)
    private String surname;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(length = 64, nullable = false)
    private String email;

    @Column(length = 1)
    private String gender;

    @Column(length = 64)
    private String address;

}
