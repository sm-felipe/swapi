package com.ame.swapi.client;

import static com.ame.swapi.controller.planets.PlanetBlockingController.BLOCKING_CONTROLLER_URI;

import com.ame.swapi.model.dto.PlanetDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

/**
 * Classe responsavel por separar as chamadas sincronas ao banco das chamadas reativas do
 * {@link com.ame.swapi.controller.planets.PlanetReactiveController}
 */
@Component
public class PlanetGateway {

    private static final int PAGE_SIZE = 10;

    private final WebClient client;

    private final String blockingAppUrl;

    public PlanetGateway(WebClient client, @Value("${blocking-app-url}") String blockingAppUrl) {
        this.client = client;
        this.blockingAppUrl = blockingAppUrl;
    }

    public Mono<PlanetDTO> save(PlanetDTO planetDTO) {

//        WebClient client = WebClient.create("locahost");
//        client.post().uri(PlanetBlockingController.BLOCKING_CONTROLLER_URI).
        return null;
    }

    public Mono<PlanetDTO> findById(Long id) {
        return client.get()
                .uri(BLOCKING_CONTROLLER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PlanetDTO.class);
    }


    public Flux<PlanetDTO> list() {

        return Flux.create(fluxSink -> {
            int page = 0;

            while (true) {
                ResponseEntity<List<PlanetDTO>> response = getPage(page);
                if(response.getStatusCode().is2xxSuccessful()) {

                    List<PlanetDTO> pagePlanets = response.getBody();
                    if (pagePlanets != null && !pagePlanets.isEmpty()) {
                        pagePlanets.forEach(fluxSink::next);
                    } else {
                        fluxSink.complete();
                        break;
                    }

                    page++;
                } else if(response.getStatusCode().isError()) {
                    fluxSink.error(new IllegalStateException("blocking app returned " +response.getStatusCode()));
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

}
