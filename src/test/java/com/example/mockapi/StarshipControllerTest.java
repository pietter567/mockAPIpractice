package com.example.mockapi;

import com.example.mockapi.web.model.Response;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = MockApiApplication.class)

@AutoConfigureWebTestClient
public class StarshipControllerTest {


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

                    case"/starships":
                        fileresponsename = "src/test/resources/fileresponse/starshipResponse.json";
                        break;
                    case"/starships/1":
                        fileresponsename = "src/test/resources/fileresponse/singleStarshipResponse.json";
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

}
