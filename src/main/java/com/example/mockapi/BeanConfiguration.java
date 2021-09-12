package com.example.mockapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfiguration {

    //taking value from configprops, swapi in main, localhost in test
    @Value("${swapiclient.host}")
    private String host;

    //bean is object that is created once in runtime, so we can save space
    //in this case, we only need one client to access host API, so we
    //just need to make client once
    @Bean("swapiWebClientBean")
    public WebClient swapiWebClient() {
        WebClient client = WebClient.builder()
                .baseUrl(host)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return client;
    }



}
