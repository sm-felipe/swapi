package com.ame.swapi.client;

import static com.ame.swapi.controller.planets.PlanetBlockingController.BLOCKING_CONTROLLER_URI;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.ame.swapi.model.dto.PlanetDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Classe responsavel por separar as chamadas sincronas ao banco das chamadas reativas do
 * {@link com.ame.swapi.controller.planets.PlanetReactiveController}
 */
@Component
public class PlanetGateway {

    private static final int PAGE_SIZE = 10;

    private final WebClient blockingAppClient;

    private final String blockingAppUrl;

    private final WebClient swapiClient;

    public PlanetGateway(@Qualifier("blockingAppClient") WebClient blockingAppClient,
                         @Value("${blocking-app-url}") String blockingAppURL,
                         @Qualifier("swapiClient") WebClient swapiClient
    ) {
        this.blockingAppClient = blockingAppClient;
        this.blockingAppUrl = blockingAppURL;
        this.swapiClient = swapiClient;
    }

    public Mono<Long> create(PlanetDTO planetDTO) {
        return blockingAppClient.post()
                .uri(BLOCKING_CONTROLLER_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(planetDTO))
                .retrieve().bodyToMono(Long.class);
    }

    public Mono<PlanetDTO> findById(Long id) {
        return blockingAppClient.get()
                .uri(BLOCKING_CONTROLLER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(NOT_FOUND::equals,
                        clientResponse -> Mono.error(new ResponseStatusException(NOT_FOUND, "Could not find the planet with id " + id)))
                .bodyToMono(PlanetDTO.class);
    }

    public Mono<PlanetDTO> findByName(String name) {
        return blockingAppClient.get()
                .uri(BLOCKING_CONTROLLER_URI + "/?name={name}", name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(NOT_FOUND::equals,
                        clientResponse -> Mono.error(new ResponseStatusException(NOT_FOUND, "Could not find the planet with name " + name)))
                .bodyToMono(PlanetDTO.class);
    }

    public Flux<PlanetDTO> list() {

        return Flux.create(fluxSink -> {
            int page = 0;

            while (true) {
                ResponseEntity<List<PlanetDTO>> response = getPage(page);
                if (response.getStatusCode().is2xxSuccessful()) {

                    List<PlanetDTO> pagePlanets = response.getBody();
                    if (pagePlanets != null && !pagePlanets.isEmpty()) {
                        pagePlanets.forEach(fluxSink::next);
                    } else {
                        fluxSink.complete();
                        break;
                    }

                    page++;
                } else if (response.getStatusCode().isError()) {
                    fluxSink.error(new IllegalStateException("blocking app returned " + response.getStatusCode()));
                }//TODO tratar outros estados
            }
        });
    }

    private ResponseEntity<List<PlanetDTO>> getPage(int page) {

        RestTemplate restTemplate = new RestTemplate();
        String url = UriComponentsBuilder.fromHttpUrl(blockingAppUrl)
                .path(BLOCKING_CONTROLLER_URI)
                .queryParam("pageSize", PAGE_SIZE)
                .queryParam("pageNumber", page).toUriString();
        return restTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<PlanetDTO>>() {
                });
    }

    public Mono deleteById(Long id) {
        return blockingAppClient.delete()
                .uri(BLOCKING_CONTROLLER_URI + "/{id}", id)
                .retrieve().bodyToMono(Object.class);
    }

    public Flux<PlanetDTO> listFromSWAPI() {
        return swapiClient.get()
                .uri("planets/?format=json")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .flatMapMany(this::convertToPlanetDTOFlux);
    }

    private Flux<PlanetDTO> convertToPlanetDTOFlux(String apiResult) {

        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return Flux.create(fluxSink -> {
            try {
                ObjectNode value = objectMapper.readValue(apiResult, ObjectNode.class);
                JsonNode results = value.get("results");
                assert results.isArray();
                for (JsonNode result : results) {
                    PlanetDTO planetDTO = objectMapper.treeToValue(result, PlanetDTO.class);
                    JsonNode films = result.get("films");
                    assert films.isArray();
                    planetDTO.setAppearingCount(films.size());
                    fluxSink.next(planetDTO);
                }
                fluxSink.complete();
            } catch (IOException e) {
                fluxSink.error(e);
            }
        });
    }
}
