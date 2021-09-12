package com.example.mockapi;

import com.example.mockapi.web.model.Response;
import com.example.mockapi.web.model.film.Film;
import com.example.mockapi.web.model.person.Person;
import com.example.mockapi.web.model.planet.Planet;
import com.example.mockapi.web.model.species.Species;
import com.example.mockapi.web.model.starships.Starship;
import com.example.mockapi.web.model.vehicle.Vehicle;
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
public class AllControllerTest {

    //create client
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

                    case"/films":
                        fileresponsename = "src/test/resources/fileresponse/filmResponse.json";
                        break;
                    case"/films/1":
                        fileresponsename = "src/test/resources/fileresponse/singleFilmResponse.json";
                        break;
                    case"/people":
                        fileresponsename = "src/test/resources/fileresponse/peopleResponse.json";
                        break;
                    case"/people/1":
                        fileresponsename = "src/test/resources/fileresponse/singlePersonResponse.json";
                        break;
                    case"/planets":
                        fileresponsename = "src/test/resources/fileresponse/planetResponse.json";
                        break;
                    case"/planets/1":
                        fileresponsename = "src/test/resources/fileresponse/singlePlanetResponse.json";
                        break;
                    case"/species":
                        fileresponsename = "src/test/resources/fileresponse/speciesResponse.json";
                        break;
                    case"/species/1":
                        fileresponsename = "src/test/resources/fileresponse/singleSpeciesResponse.json";
                        break;
                    case"/starships":
                        fileresponsename = "src/test/resources/fileresponse/starshipResponse.json";
                        break;
                    case"/starships/1":
                        fileresponsename = "src/test/resources/fileresponse/singleStarshipResponse.json";
                        break;
                    case"/vehicles":
                        fileresponsename = "src/test/resources/fileresponse/vehiclesResponse.json";
                        break;
                    case"/vehicles/1":
                        fileresponsename = "src/test/resources/fileresponse/singleVehiceResponse.json";
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

    //after all test is done, shutdown mockwebserver
    @AfterAll
    static void afterAll() throws Exception{
        mockWebServer.shutdown();
    }

    @Test
    public void getAllMoviesTest(){

        Response<List<Film>> response = client.get()
                .uri("/movies")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<Film>>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertTrue(response.getData().size() == 6);
        assertEquals(4, response.getData().get(0).getEpisode_id());
        assertEquals("A New Hope", response.getData().get(0).getTitle());

    }

    @Test
    public void getMovieBasedOnId(){

        Response<Film> response = client.get()
                .uri("/movies/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Film>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals(4, response.getData().getEpisode_id());
        assertEquals("A New Hope", response.getData().getTitle());

    }

    @Test
    public void getAllPeopleTest(){

        Response<List<Person>> response = client.get()
                .uri("/people")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<Person>>>() {})
                .returnResult()
                .getResponseBody();


        assertNotNull(response);
        assertTrue(response.getData().size() == 10);
        assertEquals("Luke Skywalker", response.getData().get(0).getName());
        assertEquals("19BBY", response.getData().get(0).getBirth_year());
        assertEquals("male", response.getData().get(0).getGender());

    }

    @Test
    public void getPersonBasedOnId(){

        Response<Person> response = client.get()
                .uri("/people/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Person>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("Luke Skywalker", response.getData().getName());
        assertEquals("male", response.getData().getGender());
        assertEquals("19BBY", response.getData().getBirth_year());

    }

    @Test
    public void getAllPlanetTest(){

        Response<List<Planet>> response = client.get()
                .uri("/planets")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<Planet>>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertTrue(response.getData().size() == 10);
        assertEquals("Tatooine", response.getData().get(0).getName());
        assertEquals("200000", response.getData().get(0).getPopulation());
        assertEquals("desert", response.getData().get(0).getTerrain());

    }

    @Test
    public void getPlanetByIdTest(){

        Response<Planet> response = client.get()
                .uri("/planets/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Planet>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("Tatooine", response.getData().getName());
        assertEquals("200000", response.getData().getPopulation());
        assertEquals("desert", response.getData().getTerrain());

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

    @Test
    public void getAllStarshipTest(){

        Response<List<Starship>> response = client.get()
                .uri("/starships")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<Starship>>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertTrue(response.getData().size() == 10);
        assertEquals("CR90 corvette", response.getData().get(0).getModel());
        assertEquals("Corellian Engineering Corporation", response.getData().get(0).getManufacturer());
        assertEquals("3500000", response.getData().get(0).getCost_in_credits());

    }

    @Test
    public void getStarshipByIdTest(){

        Response<Starship> response = client.get()
                .uri("/starships/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Starship>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("CR90 corvette", response.getData().getModel());
        assertEquals("Corellian Engineering Corporation", response.getData().getManufacturer());
        assertEquals("3500000", response.getData().getCost_in_credits());

    }

    @Test
    public void getAllVehicleTest(){

        Response<List<Vehicle>> response = client.get()
                .uri("/vehicles")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<List<Vehicle>>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertTrue(response.getData().size() == 10);
        assertEquals("Digger Crawler", response.getData().get(0).getModel());
        assertEquals("Sand Crawler", response.getData().get(0).getName());

    }

    @Test
    public void getVehicleByIdTest(){

        Response<Vehicle> response = client.get()
                .uri("/vehicles/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<Response<Vehicle>>() {})
                .returnResult()
                .getResponseBody();

        assertNotNull(response);
        assertEquals("Digger Crawler", response.getData().getModel());
        assertEquals("Sand Crawler", response.getData().getName());

    }

}
