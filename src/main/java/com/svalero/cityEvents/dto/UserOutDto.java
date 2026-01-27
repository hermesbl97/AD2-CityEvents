package com.svalero.cityEvents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDto {

    private long id;
    private String username;
    private String surname;
    private LocalDate birthDate;
}
