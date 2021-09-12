package com.example.mockapi.service;

import com.example.mockapi.web.model.planet.Planet;

import java.util.List;

public interface PlanetService {

    List<Planet> getAllPlanets();
    public Planet getPlanetById(int index);
}
