package com.ame.swapi.controller;

import com.ame.swapi.model.dto.PlanetDTO;
import com.ame.swapi.service.PlanetService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller bloqueante para interações com o banco de dados
 */
@RestController
@RequestMapping("blocking")
public class PlanetBlockingController {

    private final PlanetService planetService;

    public PlanetBlockingController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping("/planets")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody PlanetDTO planetDTO) {
        planetService.save(planetDTO);
    }

}
