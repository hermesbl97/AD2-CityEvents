package com.svalero.cityEvents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationOutDtoV2 {

    private long id;
    private String name;
    private String description;
    private String category;
    private boolean disabledAccess;
    private String city;
}
