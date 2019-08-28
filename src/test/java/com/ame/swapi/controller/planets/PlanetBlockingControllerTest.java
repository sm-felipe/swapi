package com.ame.swapi.controller.planets;

import static com.ame.swapi.controller.planets.TestCommons.TEST_ID;
import static com.ame.swapi.controller.planets.TestCommons.TEST_PLANET_NAME;
import static com.ame.swapi.controller.planets.TestCommons.createTestPlanet;
import static com.ame.swapi.controller.planets.TestCommons.createTestPlanetDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.ame.swapi.model.PlanetEntity;
import com.ame.swapi.model.dto.PlanetDTO;
import com.ame.swapi.repository.PlanetRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetBlockingControllerTest {


    @MockBean
    private PlanetRepository planetRepository;

    @Autowired
    private
    PlanetBlockingController planetBlockingController;

    @Test
    public void findById_whenPlanetExists_ReturnsPlanet() {
        PlanetEntity expectedPlanet = createTestPlanet();
        when(planetRepository.findById(TEST_ID)).thenReturn(Optional.of(expectedPlanet));

        ResponseEntity<PlanetDTO> response = planetBlockingController.findById(TEST_ID);
        PlanetDTO retrievedPlanet = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(retrievedPlanet);
        assertEquals(expectedPlanet.getName(), retrievedPlanet.getName());
        assertEquals(expectedPlanet.getClimate(), retrievedPlanet.getClimate());
        assertEquals(expectedPlanet.getTerrain(), retrievedPlanet.getTerrain());
        assertEquals(expectedPlanet.getAppearingCount(), retrievedPlanet.getAppearingCount());
    }

    @Test
    public void findById_whenPlanetDoesntExist_Returns404() {
        when(planetRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        ResponseEntity<PlanetDTO> response = planetBlockingController.findById(TEST_ID);
        PlanetDTO retrievedPlanet = response.getBody();

        assertNull(retrievedPlanet);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void findByName_whenPlanetExists_ReturnsPlanet() {
        PlanetEntity expectedPlanet = createTestPlanet();
        when(planetRepository.findByName(TEST_PLANET_NAME)).thenReturn(Optional.of(expectedPlanet));

        ResponseEntity<PlanetDTO> response = planetBlockingController.findByName(TEST_PLANET_NAME);
        PlanetDTO retrievedPlanet = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(retrievedPlanet);
        assertEquals(expectedPlanet.getName(), retrievedPlanet.getName());
        assertEquals(expectedPlanet.getClimate(), retrievedPlanet.getClimate());
        assertEquals(expectedPlanet.getTerrain(), retrievedPlanet.getTerrain());
        assertEquals(expectedPlanet.getAppearingCount(), retrievedPlanet.getAppearingCount());
    }

    @Test
    public void findByName_whenPlanetDoesntExist_Returns404() {
        when(planetRepository.findByName(TEST_PLANET_NAME)).thenReturn(Optional.empty());

        ResponseEntity<PlanetDTO> response = planetBlockingController.findByName(TEST_PLANET_NAME);
        PlanetDTO retrievedPlanet = response.getBody();

        assertNull(retrievedPlanet);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void create_whenPlanetDoesntExist_returns200() {
        PlanetEntity testPlanet = createTestPlanet();
        when(planetRepository.save(any(PlanetEntity.class))).thenReturn(testPlanet);

        Long newId = planetBlockingController.create(createTestPlanetDTO(testPlanet));

        assertEquals(testPlanet.getId(), newId);
    }

    @Test
    public void findAllPaged_whenPageExists() {
        PlanetEntity testPlanet = createTestPlanet();
        List<PlanetEntity> planetEntities = Collections.singletonList(testPlanet);
        when(planetRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(planetEntities));

        Stream<PlanetDTO> page = planetBlockingController.findAllPaged(1, 10);
        List<PlanetDTO> resultList = page.collect(Collectors.toList());
        assertEquals(planetEntities.size(), resultList.size());
        assertEquals(testPlanet.getName(), resultList.get(0).getName());
        assertEquals(testPlanet.getTerrain(), resultList.get(0).getTerrain());
        assertEquals(testPlanet.getClimate(), resultList.get(0).getClimate());
        assertEquals(testPlanet.getAppearingCount(), resultList.get(0).getAppearingCount());
    }

    @Test
    public void findAllPaged_whenPageFindsNothing() {
        when(planetRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        Stream<PlanetDTO> page = planetBlockingController.findAllPaged(1, 10);
        List<PlanetDTO> resultList = page.collect(Collectors.toList());
        assertEquals(0, resultList.size());
    }

    @Test
    public void delete_whenPlanetExists_returns200() {
        doNothing().when(planetRepository).deleteById(TEST_ID);

        ResponseEntity<Void> result = planetBlockingController.delete(TEST_ID);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test(expected = ResponseStatusException.class)
    public void delete_whenPlanetDoesntExist_returns204InException() {
        doThrow(new EmptyResultDataAccessException(1)).when(planetRepository).deleteById(TEST_ID);

        planetBlockingController.delete(TEST_ID);
    }

}