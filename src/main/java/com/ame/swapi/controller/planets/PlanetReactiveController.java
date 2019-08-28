package com.ame.swapi.controller.planets;

import com.ame.swapi.client.PlanetGateway;
import com.ame.swapi.model.dto.PlanetDTO;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Rest controller que usa WebFlux
 */
@RestController
@RequestMapping(PlanetBlockingController.PLANETS_URI)
public class PlanetReactiveController {

    private final PlanetGateway planetGateway;

    public PlanetReactiveController(PlanetGateway planetGateway) {
        this.planetGateway = planetGateway;
    }

    @PostMapping
    public Mono<PlanetDTO> create(@Valid @RequestBody PlanetDTO planetDTO) {
        return planetGateway.save(planetDTO);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<PlanetDTO> findById(@PathVariable Long id) {
        return planetGateway.findById(id);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<PlanetDTO> list() {
        return planetGateway.list();
    }

    @DeleteMapping(value = "/{id}")
    public Mono deleteById(@PathVariable Long id) {
        return planetGateway.deleteById(id);
    }

}
