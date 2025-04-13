package com.example.petoasisbackend.DTO.User.Person;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.Model.Users.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonMinimumDTO implements ModelDTO<Person> {
    private Long personId;

    public static PersonMinimumDTO fromPerson(Person person) {
        return new PersonMinimumDTO(
                person.getPersonId()
        );
    }
}
