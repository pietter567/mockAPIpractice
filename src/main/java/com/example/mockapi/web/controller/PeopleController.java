package com.example.mockapi.web.controller;


import com.example.mockapi.service.PersonService;
import com.example.mockapi.web.model.Response;
import com.example.mockapi.web.model.person.Person;
import com.example.mockapi.web.model.person.PersonResponse;
import com.example.mockapi.web.model.planet.PlanetResponse;
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
public class PeopleController {

    @Autowired
    PersonService personService;

    @GetMapping(path = "/people/{index}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<PersonResponse> getPersonById(@PathVariable("index") int index){
        Person person = personService.getPersonById(index);
        return Response.<PersonResponse>builder()
                .status(HttpStatus.OK.value())
                .data(convertToResponse(person))
                .build();
    }

    @GetMapping(path = "/people", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response<List<PersonResponse>> getAllPeople(){
        List<Person> people = personService.getAllPeople();
        List<PersonResponse> peopleResponse = people.stream().map(this::convertToResponse)
                .collect(Collectors.toList());

        return Response.<List<PersonResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(peopleResponse)
                .build();
    }

    public PersonResponse convertToResponse(Person person){

        PersonResponse personResponse = new PersonResponse();
        BeanUtils.copyProperties(person, personResponse);
        return personResponse;

    }


}
