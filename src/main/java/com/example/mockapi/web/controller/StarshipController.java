package com.example.mockapi.web.controller;

import com.example.mockapi.service.StarshipService;
import com.example.mockapi.web.model.Response;
import com.example.mockapi.web.model.film.Film;
import com.example.mockapi.web.model.film.FilmResponse;
import com.example.mockapi.web.model.species.Species;
import com.example.mockapi.web.model.species.SpeciesResponse;
import com.example.mockapi.web.model.starships.Starship;
import com.example.mockapi.web.model.starships.StarshipResponse;
import io.swagger.annotations.Api;
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
public class StarshipController {

    //dependency injection for starshipservice using autowire

    @Autowired
    private StarshipService starshipService;

    @GetMapping(path = "/starships", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<StarshipResponse>> getALlStarships(){

        List<Starship> starships = starshipService.getAllStarships();
        List<StarshipResponse> starshipResponses =
                starships.stream().map(this::convertToResponse).collect(Collectors.toList());

        return Response.<List<StarshipResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(starshipResponses)
                .build();

    }

    @GetMapping(path = "/starships/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<StarshipResponse> getSpeciesById(@PathVariable("index") int index){
        Starship starship = starshipService.getStarshipById(index);

        return Response.<StarshipResponse>builder()
                .status(HttpStatus.OK.value())
                .data(convertToResponse(starship))
                .build();

    }

    public StarshipResponse convertToResponse(Starship starship) {
        StarshipResponse starshipResponse = new StarshipResponse();
        BeanUtils.copyProperties(starship, starshipResponse);
        return starshipResponse;
    }


}
