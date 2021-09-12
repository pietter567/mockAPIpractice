package com.example.mockapi.service;

import com.example.mockapi.web.model.species.Species;

import java.util.List;

public interface SpeciesService {

    List<Species> getAllSpecies();
    Species getSpeciesById(int index);
}
