package com.example.mockapi.web.controller;

import com.example.mockapi.web.model.Response;
import com.example.mockapi.web.model.film.Film;
import com.example.mockapi.web.model.film.FilmResponse;
import com.example.mockapi.service.FilmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

//controller for movie request
@RestController
public class FilmController {

//    @Autowired
//    private FilmService filmService;

    //dependency injection for by making filmcontroller accept filmservice as parameter
    //by doing this, if there is something changing in filmservice,
    //we dont have to change film controller

    //dependency injection using constructor

    private final FilmService filmService;
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping(path = "/movies", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<FilmResponse>> getAllFilms(){

        //make new film object and change convert that to response version
        List<Film> films = filmService.getAllFilms();
        List<FilmResponse> filmResponses = films.stream().map(this::convertToResponse).collect(Collectors.toList());

        //build object response
        return Response.<List<FilmResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(filmResponses)
                .build();
    }

    @GetMapping(path = "/movies/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<FilmResponse> getFilmById(@PathVariable("index") int index){

        //make new film object and convert that to response version
        Film film = filmService.getFilmById(index);

        //build object response
        return Response.<FilmResponse>builder()
                .status(HttpStatus.OK.value())
                .data(convertToResponse(film))
                .build();
    }

    //this function changes film to filmresponse
    //we differentiate real body film and the response we get
    //because sometimes we only want to be able to access certain data
    //for example, object film might have id and we don't want user to get that
    //so we make another film object that does not have id as their properties
    //in this case, it is the same
    public FilmResponse convertToResponse(Film film) {
        FilmResponse filmResponse = new FilmResponse();
        BeanUtils.copyProperties(film, filmResponse);
        return filmResponse;
    }



}
