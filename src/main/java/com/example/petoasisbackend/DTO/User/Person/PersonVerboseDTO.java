package com.example.petoasisbackend.DTO.User.Person;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.GSU.GSUVerboseDTO;
import com.example.petoasisbackend.Model.Descriptor.Gender;
import com.example.petoasisbackend.Model.Users.GeneralSystemUser;
import com.example.petoasisbackend.Model.Users.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonVerboseDTO implements ModelDTO<Person> {
    private Long personId;
    private GSUVerboseDTO generalSystemUser;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Gender gender;
    private String address;

    public static PersonVerboseDTO fromPerson(Person person) {
        return new PersonVerboseDTO(
                person.getPersonId(),
                GSUVerboseDTO.fromGSU(person.getGeneralSystemUser()),
                person.getName(),
                person.getSurname(),
                person.getBirthDate(),
                person.getGender(),
                person.getAddress()
        );
    }
}
