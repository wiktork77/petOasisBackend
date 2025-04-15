package com.example.petoasisbackend.DTO.User.Person;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Descriptor.Gender;
import com.example.petoasisbackend.Model.Users.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonConciseDTO implements ModelDTO<Person> {
    private Long personId;
    private String name;
    private String surname;
    private Gender gender;
    private String pictureUrl;

    public static PersonConciseDTO fromPerson(Person person) {
        return new PersonConciseDTO(
                person.getPersonId(),
                person.getName(),
                person.getSurname(),
                person.getGender(),
                person.getGeneralSystemUser().getPictureUrl()
        );
    }
}
