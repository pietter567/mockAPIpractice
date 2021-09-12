package com.example.mockapi.service;


import com.example.mockapi.web.model.film.Film;

import java.util.List;


//movie service interface, shows what the model movie can do
public interface FilmService {

     List<Film> getAllFilms();
     Film getFilmById(int index);
}
