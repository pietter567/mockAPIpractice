package com.example.mockapi.service.impl;

import com.example.mockapi.SwapiResponse;
import com.example.mockapi.service.SpeciesService;
import com.example.mockapi.web.model.planet.Planet;
import com.example.mockapi.web.model.species.Species;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;

import java.util.List;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    @Autowired
    @Qualifier("swapiWebClientBean")
    private WebClient swapiWebClient;

    @Override
    public List<Species> getAllSpecies() {

        SwapiResponse<Species> response = swapiWebClient.get()
                .uri("/species")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Species>>() {})
                .block();

        return response.getResults();
    }

    @Override
    public Species getSpeciesById(int index) {
        Species response = swapiWebClient.get()
                .uri("/species/" + index)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Species>() {})
                .block();

        return response;
    }
}
