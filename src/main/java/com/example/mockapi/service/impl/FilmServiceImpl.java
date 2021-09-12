package com.example.mockapi.service.impl;

import com.example.mockapi.SwapiResponse;
import com.example.mockapi.service.FilmService;
import com.example.mockapi.web.model.film.Film;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    //add client object
    @Autowired
    @Qualifier("swapiWebClientBean")
    private WebClient swapiWebClient;

    //function for client to get data from host
    @Override
    public List<Film> getAllFilms() {
        SwapiResponse<Film> response = swapiWebClient.get()
                .uri("/films")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Film>>() {})
                .block();

        return response.getResults();
    }

    @Override
    public Film getFilmById(int index) {
        Film response = swapiWebClient.get()
                .uri("/films/" + index)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Film>() {})
                .block();

        return response;
    }

}
