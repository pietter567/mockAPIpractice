package com.example.mockapi.web.model.film;


//this is the response when we ask for movie
//

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilmResponse {

    private String title;
    private Integer episode_id;

}
