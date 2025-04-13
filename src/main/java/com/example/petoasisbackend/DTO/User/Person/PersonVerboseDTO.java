package com.example.petoasisbackend.DTO.User.Person;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Users.Gender;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Person;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonVerboseDTO implements ModelDTO<Person> {
    private Long personId;
    private GeneralSystemUser generalSystemUser;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Gender gender;
    private String address;

    public static PersonVerboseDTO fromPerson(Person person) {
        return new PersonVerboseDTO(
                person.getPersonId(),
                person.getGeneralSystemUser(),
                person.getName(),
                person.getSurname(),
                person.getBirthDate(),
                person.getGender(),
                person.getAddress()
        );
    }
}
