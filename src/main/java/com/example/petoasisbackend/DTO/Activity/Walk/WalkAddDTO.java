package com.example.petoasisbackend.DTO.Activity.Walk;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class WalkAddDTO {
    private Long animalId;

    private Long personId;

    private Long shelterId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
