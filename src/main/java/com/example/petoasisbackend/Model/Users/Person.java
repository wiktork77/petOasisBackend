package com.example.petoasisbackend.Model.Users;


import com.example.petoasisbackend.Model.Enum.Gender;
import com.example.petoasisbackend.Request.User.Person.PersonAddRequest;
import com.example.petoasisbackend.Request.User.Person.PersonUpdateRequest;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "system_user_id")
    private GeneralSystemUser generalSystemUser;

    @Column(length = 32, nullable = false)
    private String name;

    @Column(length = 48, nullable = false)
    private String surname;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(length = 1)
    private Gender gender;

    @Column(length = 64)
    private String address;

    public Person(String name, String surname, LocalDate birthDate, Gender gender, String address) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personId, person.personId) && Objects.equals(generalSystemUser, person.generalSystemUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(personId, generalSystemUser);
    }

    public static Person fromPersonAddRequest(PersonAddRequest request) {
        return new Person(
                request.getName(),
                request.getSurname(),
                request.getBirthDate(),
                request.getGender(),
                request.getAddress()
        );
    }

    public void update(PersonUpdateRequest request) {
        this.name = request.getName();
        this.surname = request.getSurname();
        this.birthDate = request.getBirthDate();
        this.gender = request.getGender();
        this.address = request.getAddress();
    }
}
