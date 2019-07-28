package com.ame.swapi.client;

import static com.ame.swapi.controller.planets.PlanetBlockingController.BLOCKING_CONTROLLER_URI;

import com.ame.swapi.model.dto.PlanetDTO;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Classe responsavel por separar as chamadas sincronas ao banco das chamadas reativas do
 * {@link com.ame.swapi.controller.planets.PlanetReactiveController}
 */
@Component
public class PlanetGateway {

    public Mono<PlanetDTO> save(PlanetDTO planetDTO) {

//        WebClient client = WebClient.create("locahost");
//        client.post().uri(PlanetBlockingController.BLOCKING_CONTROLLER_URI).
        return null;
    }

    public Mono<PlanetDTO> findById(Long id) {
        WebClient client = WebClient.create("http://127.0.0.1:8080");//TODO obter de modo melhor a url
        return client.get()
                .uri(BLOCKING_CONTROLLER_URI + "/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PlanetDTO.class);
    }
}
