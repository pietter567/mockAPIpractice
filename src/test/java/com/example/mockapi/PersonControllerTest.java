package com.example.mockapi;

import com.example.mockapi.web.model.Response;
import com.example.mockapi.web.model.person.Person;
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
public class PersonControllerTest {

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
                    case"/people":
                        fileresponsename = "src/test/resources/fileresponse/peopleResponse.json";
                        break;
                    case"/people/1":
                        fileresponsename = "src/test/resources/fileresponse/singlePersonResponse.json";
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


}
