package com.example.mockapi.service.impl;

import com.example.mockapi.SwapiResponse;
import com.example.mockapi.service.VehicleService;
import com.example.mockapi.web.model.starships.Starship;
import com.example.mockapi.web.model.vehicle.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    @Qualifier("swapiWebClientBean")
    private WebClient swapiWebClient;

    @Override
    public List<Vehicle> getALlVehicles() {

        SwapiResponse<Vehicle> response = swapiWebClient.get()
                .uri("/vehicles")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Vehicle>>() {})
                .block();

        return response.getResults();

    }

    @Override
    public Vehicle getVehicleById(int index) {
        Vehicle response = swapiWebClient.get()
                .uri("/vehicles/" + index)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Vehicle>() {})
                .block();

        return response;
    }

}
