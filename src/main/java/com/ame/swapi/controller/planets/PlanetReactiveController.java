package com.ame.swapi.controller.planets;

import com.ame.swapi.client.PlanetGateway;
import com.ame.swapi.model.dto.PlanetDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Rest controller que usa WebFlux
 */
@RestController
@RequestMapping(PlanetBlockingController.PLANETS_URI)
@Api(value = "/planets", tags = "planets")
public class PlanetReactiveController {

    private final PlanetGateway planetGateway;

    public PlanetReactiveController(PlanetGateway planetGateway) {
        this.planetGateway = planetGateway;
    }

    @PostMapping
    @ApiOperation(value = "creates a new planet at database")
    public Mono<Long> create(@Valid @RequestBody PlanetDTO planetDTO) {
        return planetGateway.create(planetDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ApiOperation(value = "lists all saved planets")
    public Flux<PlanetDTO> list() {
        return planetGateway.list();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "finds a planet by id")
    public Mono<PlanetDTO> findById(@PathVariable Long id) {
        return planetGateway.findById(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "name")
    @ApiOperation(value = "finds a planet by name")
    public Mono<PlanetDTO> findByName(@RequestParam String name) {
        return planetGateway.findByName(name);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "removes a planet")
    public Mono deleteById(@PathVariable Long id) {
        return planetGateway.deleteById(id);
    }

    @GetMapping(params = "swapi", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    @ApiOperation(value = "lists all planets at STAR WARS API")
    public Flux<PlanetDTO> listFromSWAPI() {
        return planetGateway.listFromSWAPI();
    }

}
