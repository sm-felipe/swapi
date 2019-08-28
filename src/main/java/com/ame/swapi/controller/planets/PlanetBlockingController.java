package com.ame.swapi.controller.planets;

import com.ame.swapi.model.dto.PlanetDTO;
import com.ame.swapi.service.PlanetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller bloqueante para interacoes com o banco de dados
 */
@RestController
@RequestMapping(PlanetBlockingController.BLOCKING_CONTROLLER_URI)
@Api(value = "/blocking/planets", tags = "planets")
public class PlanetBlockingController {

    static final String PLANETS_URI = "planets";
    public static final String BLOCKING_CONTROLLER_URI = "blocking/" + PLANETS_URI;

    private final PlanetService planetService;

    public PlanetBlockingController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "creates a new planet at database")
    public Long create(@Valid @RequestBody PlanetDTO planetDTO) {
        return planetService.save(planetDTO);
    }

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE, params = {"pageNumber", "pageSize"})
    @ResponseBody
    @ApiOperation(value = "lists all saved planets")
    public Stream<PlanetDTO> findAllPaged(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return planetService.findAllPaged(pageNumber, pageSize);
    }

    @GetMapping(value = "/{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "finds a planet by id")
    public ResponseEntity<PlanetDTO> findById(@PathVariable Long id) {
        Optional<PlanetDTO> planet = planetService.findById(id);
        return planet.map(planetDTO -> new ResponseEntity<>(planetDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, params = "name")
    @ResponseBody
    @ApiOperation(value = "finds a planet by name")
    public ResponseEntity<PlanetDTO> findByName(@RequestParam String name) {
        Optional<PlanetDTO> planet = planetService.findByName(name);
        return planet.map(planetDTO -> new ResponseEntity<>(planetDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "removes a planet")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        planetService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
