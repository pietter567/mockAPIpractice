package com.example.mockapi.service.impl;

import com.example.mockapi.SwapiResponse;
import com.example.mockapi.service.PlanetService;
import com.example.mockapi.web.model.film.Film;
import com.example.mockapi.web.model.person.Person;
import com.example.mockapi.web.model.planet.Planet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PlanetServiceImpl implements PlanetService {

    @Autowired
    @Qualifier("swapiWebClientBean")
    private WebClient swapiWebClient;

    @Override
    public List<Planet> getAllPlanets() {
        SwapiResponse<Planet> response = swapiWebClient.get()
                .uri("/planets")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Planet>>() {})
                .block();

        return response.getResults();
    }

    @Override
    public Planet getPlanetById(int index) {
        Planet response = swapiWebClient.get()
                .uri("/planets/" + index)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Planet>() {})
                .block();

        return response;
    }
}
