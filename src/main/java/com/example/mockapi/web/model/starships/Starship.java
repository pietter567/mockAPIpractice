package com.example.mockapi.web.model.starships;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Starship {

    private String model;
    private String manufacturer;
    private String cost_in_credits;

}
