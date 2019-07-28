package com.ame.swapi.controller.planets;

import com.ame.swapi.model.dto.PlanetDTO;
import com.ame.swapi.service.PlanetService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller bloqueante para intera��es com o banco de dados
 */
@RestController
@RequestMapping(PlanetBlockingController.BLOCKING_CONTROLLER_URI)
public class PlanetBlockingController {

    public static final String PLANETS_URI = "planets";
    public static final String BLOCKING_CONTROLLER_URI = "blocking/" + PLANETS_URI;

    private final PlanetService planetService;

    public PlanetBlockingController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody PlanetDTO planetDTO) {
        planetService.save(planetDTO);
    }

    @GetMapping(value = "/{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PlanetDTO findById(@PathVariable Long id) {
        return planetService.findById(id).orElse(null);
    }

}