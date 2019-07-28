package com.ame.swapi.controller.planets;

import static org.junit.Assert.assertNotNull;
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
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetBlockingControllerTest {

    @MockBean
    private PlanetRepository planetRepository;

    @Autowired
    PlanetBlockingController planetBlockingController;

    @Test
    public void findById() {
        when(planetRepository.findById(1L)).thenReturn(Optional.of(new PlanetEntity()));

        PlanetDTO byId = planetBlockingController.findById(1L);

        assertNotNull(byId);
    }

}