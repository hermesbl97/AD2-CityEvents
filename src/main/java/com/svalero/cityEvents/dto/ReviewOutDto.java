package com.svalero.cityEvents.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewOutDto {
    private long id;
    private float rate;
    private String comment;
    private LocalDate registerDate;
    private int likes;
    private boolean recommend;

}
