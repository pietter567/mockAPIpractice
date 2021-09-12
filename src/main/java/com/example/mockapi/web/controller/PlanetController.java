package com.example.mockapi.web.controller;

import com.example.mockapi.service.PlanetService;
import com.example.mockapi.web.model.Response;
import com.example.mockapi.web.model.planet.Planet;
import com.example.mockapi.web.model.planet.PlanetResponse;
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
public class PlanetController {

    @Autowired
    private PlanetService planetService;

    @GetMapping(path = "/planets", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<PlanetResponse>> getAllPlanets(){

        List<Planet> planets = planetService.getAllPlanets();
        List<PlanetResponse> planetResponses =
                planets.stream().map(this::convertToResponse).collect(Collectors.toList());

        return Response.<List<PlanetResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(planetResponses)
                .build();

    }

    @GetMapping(path = "/planets/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<PlanetResponse> getPlanetById(@PathVariable("index") int index){
        Planet planet = planetService.getPlanetById(index);

        return Response.<PlanetResponse>builder()
                .status(HttpStatus.OK.value())
                .data(convertToResponse(planet))
                .build();

    }

    public PlanetResponse convertToResponse(Planet planet) {
        PlanetResponse planetResponse = new PlanetResponse();
        BeanUtils.copyProperties(planet, planetResponse);
        return planetResponse;
    }



}
