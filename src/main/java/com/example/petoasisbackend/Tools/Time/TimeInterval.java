package com.example.petoasisbackend.Tools.Time;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TimeInterval {
    private LocalDateTime start;
    private LocalDateTime finish;
}
