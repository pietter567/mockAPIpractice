package com.example.mockapi.service;

import com.example.mockapi.web.model.person.Person;

import java.util.List;

public interface PersonService {

    List<Person> getAllPeople();
    Person getPersonById(int index);
}
