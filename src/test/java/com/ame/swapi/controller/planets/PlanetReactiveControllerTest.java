package com.ame.swapi.controller.planets;

import static com.ame.swapi.controller.planets.TestCommons.TEST_ID;
import static com.ame.swapi.controller.planets.TestCommons.TEST_PLANET_NAME;
import static com.ame.swapi.controller.planets.TestCommons.createTestPlanetDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
        planetReactiveController = new PlanetReactiveController(new PlanetGateway(client, baseUrl, client));
    }

    @Test
    public void findById_whenPlanetExists_ReturnsPlanet() throws JsonProcessingException {
        PlanetDTO expectedPlanet = new PlanetDTO("name", "temperate", "mountains", 1);

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(expectedPlanet))
        );

        PlanetDTO retrievedPlanet = planetReactiveController.findById(TEST_ID).block();

        assertEquals(expectedPlanet, retrievedPlanet);
    }

    @Test(expected = ResponseStatusException.class)
    public void findById_whenPlanetDoesntExist_Returns404() {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(HttpStatus.NOT_FOUND.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );

        planetReactiveController.findById(TEST_ID).block();
    }

    @Test
    public void findByName_whenPlanetExists_ReturnsPlanet() throws JsonProcessingException {
        PlanetDTO expectedPlanet = new PlanetDTO("name", "temperate", "mountains", 1);

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(expectedPlanet))
        );

        PlanetDTO retrievedPlanet = planetReactiveController.findByName(TEST_PLANET_NAME).block();

        assertEquals(expectedPlanet, retrievedPlanet);
    }

    @Test(expected = ResponseStatusException.class)
    public void findByName_whenPlanetDoesntExist_Returns404() {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(HttpStatus.NOT_FOUND.value())
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        );

        planetReactiveController.findByName(TEST_PLANET_NAME).block();
    }

    @Test
    public void create_whenPlanetDoesntExist_returns200() {
        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody("1")
        );

        Mono<Long> newId = planetReactiveController.create(createTestPlanetDTO());

        assertEquals(Long.valueOf(1), newId.block());
    }

    @Test
    public void list_With2Pages() throws JsonProcessingException {

        PlanetDTO planetDTO = new PlanetDTO("name1", "climate1", "swamps", 1);
        PlanetDTO planetDTO2 = new PlanetDTO("name2", "climate1", "swamps", 2);
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
        List<PlanetDTO> resultList = result.collectList().block();
        assert resultList != null;
        assertEquals(2, resultList.size());
        assertEquals(planetDTO, resultList.get(0));
        assertEquals(planetDTO2, resultList.get(1));
    }

    @Test
    public void list_WithEmptyFirstPage() throws JsonProcessingException {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_STREAM_JSON_VALUE)
                        .setBody(objectMapper.writeValueAsString(List.of()))
        );

        Flux<PlanetDTO> result = planetReactiveController.list();
        List<PlanetDTO> resultList = result.collectList().block();
        assert resultList != null;
        assertTrue(resultList.isEmpty());
    }

    @Test
    public void delete() {

        mockWebServer.enqueue(
                new MockResponse()
                        .setResponseCode(200)
        );

        Mono deleted = planetReactiveController.deleteById(1L);
        Object deleteReturn = deleted.block();
        assertNull(deleteReturn);
    }
}