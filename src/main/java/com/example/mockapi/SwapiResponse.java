package com.example.mockapi;

import com.example.mockapi.web.model.film.Film;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

//this object is basically the whole response from Swapi

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SwapiResponse<T> {

    private List<T> results;
    private T result;

}
