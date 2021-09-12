package com.example.mockapi.web.controller;

import com.example.mockapi.service.SpeciesService;
import com.example.mockapi.web.model.Response;
import com.example.mockapi.web.model.planet.Planet;
import com.example.mockapi.web.model.planet.PlanetResponse;
import com.example.mockapi.web.model.species.Species;
import com.example.mockapi.web.model.species.SpeciesResponse;
import com.example.mockapi.web.model.starships.Starship;
import com.example.mockapi.web.model.starships.StarshipResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class SpeciesController {

    @Autowired
    SpeciesService speciesService;

    @GetMapping(path = "/species", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<SpeciesResponse>> getAllSpecies(){
        List<Species> species = speciesService.getAllSpecies();
        List<SpeciesResponse> speciesResponse = species.stream().map(this::convertToResponse).collect(Collectors.toList());

        return Response.<List<SpeciesResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(speciesResponse)
                .build();

    }

    @GetMapping(path = "/species/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<SpeciesResponse> getSpeciesById(@PathVariable("index") int index){
        Species species = speciesService.getSpeciesById(index);

        return Response.<SpeciesResponse>builder()
                .status(HttpStatus.OK.value())
                .data(convertToResponse(species))
                .build();

    }

    public SpeciesResponse convertToResponse(Species species) {
        SpeciesResponse speciesResponse = new SpeciesResponse();
        BeanUtils.copyProperties(species, speciesResponse);
        return speciesResponse;
    }

}
