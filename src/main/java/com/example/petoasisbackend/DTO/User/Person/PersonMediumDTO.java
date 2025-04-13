package com.example.petoasisbackend.DTO.User.Person;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Users.Gender;
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
    private String pictureUrl;
    private LocalDate birthDate;
    private String phoneNumber;

    public static PersonMediumDTO fromPerson(Person person) {
        return new PersonMediumDTO(
                person.getPersonId(),
                person.getName(),
                person.getSurname(),
                person.getGender(),
                person.getGeneralSystemUser().getPictureUrl(),
                person.getBirthDate(),
                person.getGeneralSystemUser().getPhoneNumber()
        );
    }
}
