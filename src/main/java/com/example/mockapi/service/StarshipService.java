package com.example.mockapi.service;

import com.example.mockapi.web.model.starships.Starship;

import java.util.List;

public interface StarshipService {

    List<Starship> getAllStarships();
    Starship getStarshipById(int index);
}
