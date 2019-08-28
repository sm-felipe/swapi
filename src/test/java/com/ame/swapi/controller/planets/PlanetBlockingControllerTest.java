package com.ame.swapi.controller.planets;

import static com.ame.swapi.controller.planets.TestCommons.TEST_ID;
import static com.ame.swapi.controller.planets.TestCommons.TEST_PLANET_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.ame.swapi.model.PlanetEntity;
import com.ame.swapi.model.dto.PlanetDTO;
import com.ame.swapi.repository.PlanetRepository;
import java.util.Optional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
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

    private PlanetEntity createTestPlanet() {
        PlanetEntity expectedPlanet = new PlanetEntity();
        expectedPlanet.setId(1L);
        expectedPlanet.setClimate("arid");
        expectedPlanet.setTerrain("mountains");
        expectedPlanet.setAppearingCount(3);
        return expectedPlanet;
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