package com.ame.swapi.controller.planets;

import com.ame.swapi.model.PlanetEntity;
import com.ame.swapi.model.dto.PlanetDTO;
import org.springframework.beans.BeanUtils;

public interface TestCommons {

     long TEST_ID = 1L;
     String TEST_PLANET_NAME = "NAME1";

     static PlanetDTO createTestPlanetDTO() {
          PlanetDTO planetDTO = new PlanetDTO();
          BeanUtils.copyProperties(createTestPlanet(), planetDTO);
          return planetDTO;
     }

     static PlanetDTO createTestPlanetDTO(PlanetEntity testPlanet) {
          PlanetDTO planetDTO = new PlanetDTO();
          BeanUtils.copyProperties(testPlanet, planetDTO);
          return planetDTO;
     }

     static PlanetEntity createTestPlanet() {
          PlanetEntity expectedPlanet = new PlanetEntity();
          expectedPlanet.setId(1L);
          expectedPlanet.setClimate("arid");
          expectedPlanet.setTerrain("mountains");
          expectedPlanet.setAppearingCount(3);
          return expectedPlanet;
     }
}
