package com.example.petoasisbackend.Tools.Time;

import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class Period {
    private LocalDateTime start;

    private LocalDateTime end;

    public Period(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

}
