package com.example.petoasisbackend.DTO.User.Person;

import com.example.petoasisbackend.Model.Enum.Gender;
import com.example.petoasisbackend.Model.Users.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonUpdateDTO {
    private Long personId;

    private String name;

    private String surname;

    private LocalDate birthDate;

    private Gender gender;

    private String address;

    public static PersonUpdateDTO fromPerson(Person person) {
        return new PersonUpdateDTO(
                person.getPersonId(),
                person.getName(),
                person.getSurname(),
                person.getBirthDate(),
                person.getGender(),
                person.getAddress()
        );
    }
}
