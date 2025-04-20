package com.example.petoasisbackend.DTO.User.Person;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUMediumDTO;
import com.example.petoasisbackend.Model.Enum.Gender;
import com.example.petoasisbackend.Model.Users.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonMediumDTO implements ModelDTO<Person> {
    private Long personId;
    private String name;
    private String surname;
    private Gender gender;
    private LocalDate birthDate;

    private GSUMediumDTO gsu;
    public static PersonMediumDTO fromPerson(Person person) {
        return new PersonMediumDTO(
                person.getPersonId(),
                person.getName(),
                person.getSurname(),
                person.getGender(),
                person.getBirthDate(),
                GSUMediumDTO.fromGSU(person.getGeneralSystemUser())
        );
    }
}
