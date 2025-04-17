package com.example.petoasisbackend.DTO.Activity.Walk;


import com.example.petoasisbackend.DTO.Animal.Animal.AnimalAvailabilityDTO;
import com.example.petoasisbackend.DTO.ModelDTO;
import com.example.petoasisbackend.DTO.Status.WalkStatus.WalkStatusNameDTO;
import com.example.petoasisbackend.Model.Activity.Walk;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WalkChangeStatusDTO implements ModelDTO<Walk> {
    private Long walkId;
    private WalkStatusNameDTO status;
    private AnimalAvailabilityDTO animal;

    public static WalkChangeStatusDTO fromWalk(Walk walk) {
        return new WalkChangeStatusDTO(
                walk.getWalkId(),
                WalkStatusNameDTO.fromWalkStatus(walk.getWalkStatus()),
                AnimalAvailabilityDTO.fromAnimal(walk.getPupil())
        );
    }
}
