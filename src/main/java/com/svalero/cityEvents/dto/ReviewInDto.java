package com.svalero.cityEvents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewInDto {

    private LocalDate registerDate;
    private boolean recommend;
    private boolean visible;
    private int rate;
    private String comment;
    private long eventId;
    private long userId;
}
