package com.svalero.cityEvents.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInDto {

    @NotNull(message = "Username is mandatory")
    private String username;
    @NotNull(message = "Name is mandatory")
    private String name;
    @NotNull(message = "The surname is mandatory")
    private String surname;
    private LocalDate birthDate;
    @Min(value = 600000000, message = "The telephone must have this structure 6XX XXX XXX")
    @Max(value = 699999999, message = "The telephone must have this structure 6XX XXX XXX")
    private int telephoneNumber;

}
