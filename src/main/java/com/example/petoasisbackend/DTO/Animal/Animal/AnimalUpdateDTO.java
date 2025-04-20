package com.example.petoasisbackend.DTO.Animal.Animal;

import com.example.petoasisbackend.Model.Animal.Animal;
import com.example.petoasisbackend.Model.Enum.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnimalUpdateDTO {
    private Long animalId;

    private String name;

    private Float weight;

    private Float height;

    private Float length;

    private LocalDate dateOfBirth;

    private Boolean isNeutered;

    private Gender gender;

    private Boolean enjoysPetting;

    private String description;

    public static AnimalUpdateDTO fromAnimal(Animal animal) {
        return new AnimalUpdateDTO(
                animal.getAnimalId(),
                animal.getName(),
                animal.getWeight(),
                animal.getHeight(),
                animal.getLength(),
                animal.getDateOfBirth(),
                animal.getIsNeutered(),
                animal.getGender(),
                animal.getEnjoysPetting(),
                animal.getDescription()
        );
    }
}
