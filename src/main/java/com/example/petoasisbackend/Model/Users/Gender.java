package com.example.petoasisbackend.Model.Users;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public enum Gender {
    MALE("M"),
    FEMALE("F"),
    UNKNOWN("U");

    private final String value;

    Gender(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return this.value;
    }

    @JsonCreator
    public static Gender fromValue(String value) {
        for (Gender gender : Gender.values()) {
            if (gender.getValue().equalsIgnoreCase(value)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unknown gender: " + value);
    }
}
