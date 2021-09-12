package com.example.mockapi;

import com.example.mockapi.web.model.Response;
import com.example.mockapi.web.model.planet.Planet;
import com.example.mockapi.web.model.species.Species;
import com.example.mockapi.web.model.starships.Starship;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MockApiApplication.class)

@AutoConfigureWebTestClient
public class SpeciesControllerTest {

    @Autowired
    WebTestClient client;

    static MockWebServer mockWebServer;

    //before all, start mockwebserver
    @BeforeAll
    static void beforeAll() throws Exception{

        mockWebServer = new MockWebServer();
        mockWebServer.start(10001);

        mockWebServer.setDispatcher(new Dispatcher() {
            @Override
            public MockResponse dispatch(RecordedRequest recordedRequest) throws InterruptedException {
                MockResponse mockResponse = new MockResponse();
                mockResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                mockResponse.setResponseCode(200);

                String fileresponsename = null;

                switch(recordedRequest.getPath()){

                    case"/species":
                        fileresponsename = "src/test/resources/fileresponse/speciesResponse.json";
                        break;
                    case"/species/1":
                        fileresponsename = "src/test/resources/fileresponse/singleSpeciesResponse.json";
                        break;
                }

                try{
                    FileInputStream fileInputStream = new FileInputStream(fileresponsename);
                    String content = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8.name());
                    mockResponse.setBody(content);
                } catch (Exception e) {
                    System.out.println("error" + e.getMessage());
                }

                return mockResponse;
            }
        });

    }

    @AfterAll
    static void afterAll() throws Exception{
        mockWebServer.shutdown();
    }

    @Test
    public void getAllSpeciesTest(){

        Response<List<Species>> response = client.get()
                .uri("/species")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<Species>>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertTrue(response.getData().size() == 10);
        assertEquals("Human", response.getData().get(0).getName());
        assertEquals("mammal", response.getData().get(0).getClassification());
        assertEquals("sentient", response.getData().get(0).getDesignation());

    }

    @Test
    public void getSpeciesByIdTest(){

        Response<Species> response = client.get()
                .uri("/species/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Species>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("Human", response.getData().getName());
        assertEquals("mammal", response.getData().getClassification());
        assertEquals("sentient", response.getData().getDesignation());

    }

}
