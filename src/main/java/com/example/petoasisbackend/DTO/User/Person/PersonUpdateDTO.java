package com.example.petoasisbackend.DTO.User.Person;

import com.example.petoasisbackend.Model.Users.Gender;
import com.example.petoasisbackend.Model.Users.Person;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
