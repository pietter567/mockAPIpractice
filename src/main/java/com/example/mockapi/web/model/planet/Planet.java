package com.example.mockapi.web.model.planet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Planet {

    private String name;
    private String terrain;
    private String population;
}
