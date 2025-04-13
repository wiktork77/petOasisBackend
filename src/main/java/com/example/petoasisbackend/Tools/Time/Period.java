package com.example.petoasisbackend.Tools.Time;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class Period {
    @NotBlank(message = "must not be blank")
    private LocalDateTime start;

    @NotBlank(message = "must not be blank")
    private LocalDateTime finish;

    public Period(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.finish = end;
    }
}
