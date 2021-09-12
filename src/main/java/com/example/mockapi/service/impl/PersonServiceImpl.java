package com.example.mockapi.service.impl;

import com.example.mockapi.SwapiResponse;
import com.example.mockapi.service.PersonService;
import com.example.mockapi.web.model.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    @Qualifier("swapiWebClientBean")
    private WebClient swapiWebClient;

    @Override
    public List<Person> getAllPeople() {
        SwapiResponse<Person> response = swapiWebClient.get()
                .uri("/people")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<SwapiResponse<Person>>() {})
                .block();

        return response.getResults();
    }

    @Override
    public Person getPersonById(int index) {
        Person response = swapiWebClient.get()
                .uri("/people/" + index)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Person>() {})
                .block();

        return response;
    }


}
