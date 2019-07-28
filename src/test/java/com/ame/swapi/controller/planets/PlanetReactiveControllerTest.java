package com.ame.swapi.controller.planets;

import com.ame.swapi.client.PlanetGateway;
import com.ame.swapi.model.dto.PlanetDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

//@WebFluxTest
@RunWith(MockitoJUnitRunner.class)
public class PlanetReactiveControllerTest {

    MockWebServer mockWebServer = new MockWebServer();

    PlanetReactiveController planetReactiveController;

    ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        WebClient client = WebClient.create(mockWebServer.url("/").toString());
        planetReactiveController = new PlanetReactiveController(new PlanetGateway(client));
    }

    @Test
    public void findById() throws JsonProcessingException {
        PlanetDTO planetDTO = new PlanetDTO("name", "clinamte", "dsfs", 1);

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(planetDTO))
        );

        PlanetDTO block = planetReactiveController.findById(1L).block();
        System.out.println(block);

    }
}