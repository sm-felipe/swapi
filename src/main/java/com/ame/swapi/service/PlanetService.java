package com.ame.swapi.service;

import com.ame.swapi.model.PlanetEntity;
import com.ame.swapi.model.dto.PlanetDTO;
import com.ame.swapi.repository.PlanetRepository;
import java.util.Optional;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service principal que interage com o modelo do {@link PlanetEntity}
 */
@Service
public class PlanetService {

    private final PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    @Transactional
    public void save(PlanetDTO planetDTO) {
        PlanetEntity newEntity = new PlanetEntity();
        BeanUtils.copyProperties(planetDTO, newEntity);
        planetRepository.save(newEntity);
    }

    public Iterable<PlanetEntity> findAllSalved() {
        return planetRepository.findAll();
    }

    public Iterable<PlanetEntity> findAllAtStarWarsAPI() {
        return null;
    }

    public Optional<PlanetEntity> findByName(String name) {
        return planetRepository.findByName(name);
    }

    public Optional<PlanetDTO> findById(Long id) {
        Optional<PlanetEntity> entity = planetRepository.findById(id);
        if(entity.isPresent()) {
            PlanetDTO convertedPlanet = new PlanetDTO();
            BeanUtils.copyProperties(entity.get(), convertedPlanet);
            return Optional.of(convertedPlanet);
        }
        return Optional.empty();
    }

    @Transactional
    public void delete(Long id) {
        planetRepository.deleteById(id);
    }

}
