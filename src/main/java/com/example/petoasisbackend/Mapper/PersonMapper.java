package com.example.petoasisbackend.Mapper;

import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonConciseDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonMediumDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonMinimumDTO;
import com.example.petoasisbackend.DTO.User.Person.PersonVerboseDTO;
import com.example.petoasisbackend.Model.Users.Person;
import com.example.petoasisbackend.Request.DataDetailLevel;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class PersonMapper implements DetailLevelMapper<Person> {
    @Override
    public Function<Person, ModelDTO<Person>> getMapper(DataDetailLevel level) {
        switch (level) {
            case VERBOSE -> {
                return PersonVerboseDTO::fromPerson;
            }
            case MEDIUM -> {
                return PersonMediumDTO::fromPerson;
            }
            case CONCISE -> {
                return PersonConciseDTO::fromPerson;
            }
            case MINIMUM -> {
                return PersonMinimumDTO::fromPerson;
            }
            default -> {
                return PersonMinimumDTO::fromPerson;
            }
        }
    }
}
