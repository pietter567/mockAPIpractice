package com.example.mockapi.service.impl;

import com.example.mockapi.SwapiResponse;
import com.example.mockapi.service.StarshipService;
import com.example.mockapi.web.model.species.Species;
import com.example.mockapi.web.model.starships.Starship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class StarshipServiceImpl implements StarshipService {

    @Autowired
    @Qualifier("swapiWebClientBean")
    private WebClient swapiWebClient;

    //function for client to get data from host
    @Override
    public List<Starship> getAllStarships() {
        SwapiResponse<Starship> response = swapiWebClient.get()
                .uri("/starships")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Starship>>() {})
                .block();

        return response.getResults();
    }

    @Override
    public Starship getStarshipById(int index) {
        Starship response = swapiWebClient.get()
                .uri("/starships/" + index)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Starship>() {})
                .block();

        return response;
    }

}
