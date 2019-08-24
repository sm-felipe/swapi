package com.ame.swapi.controller.planets;

import com.ame.swapi.client.PlanetGateway;
import com.ame.swapi.model.dto.PlanetDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

//@WebFluxTest
@RunWith(MockitoJUnitRunner.class)
public class PlanetReactiveControllerTest {

    private MockWebServer mockWebServer = new MockWebServer();

    private PlanetReactiveController planetReactiveController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init() {
        String baseUrl = mockWebServer.url("/").toString();
        WebClient client = WebClient.create(baseUrl);
        planetReactiveController = new PlanetReactiveController(new PlanetGateway(client, baseUrl));
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

    @Test
    public void listPlanets() throws JsonProcessingException {

        PlanetDTO planetDTO = new PlanetDTO("name1", "clinamte", "dsfs", 1);
        PlanetDTO planetDTO2 = new PlanetDTO("name2", "clinamte", "dsfs", 1);
        List<PlanetDTO> planetList = List.of(planetDTO, planetDTO2);

        //first page gives some elements
        mockWebServer.enqueue(
                new MockResponse()
                .setResponseCode(200)
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_STREAM_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(planetList))
        );

        //second page gives none so the flux can complete
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_STREAM_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(List.of()))
        );

        Flux<PlanetDTO> result = planetReactiveController.list();
        result.subscribe(System.out::println);
    }
}