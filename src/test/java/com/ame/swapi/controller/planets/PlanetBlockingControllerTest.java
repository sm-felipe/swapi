package com.ame.swapi.controller.planets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    private static final long TEST_ID = 1L;
    @MockBean
    private PlanetRepository planetRepository;

    @Autowired
    private
    PlanetBlockingController planetBlockingController;

    @Test
    public void findById() {
        when(planetRepository.findById(TEST_ID)).thenReturn(Optional.of(new PlanetEntity()));

        ResponseEntity<PlanetDTO> byId = planetBlockingController.findById(TEST_ID);

        assertNotNull(byId.getBody());
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