package com.ame.swapi.service;

import com.ame.swapi.model.PlanetEntity;
import com.ame.swapi.model.dto.PlanetDTO;
import com.ame.swapi.repository.PlanetRepository;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Long save(PlanetDTO planetDTO) {
        PlanetEntity newEntity = new PlanetEntity();
        BeanUtils.copyProperties(planetDTO, newEntity);
        PlanetEntity savedPlanet = planetRepository.save(newEntity);
        return savedPlanet.getId();
    }

    public Stream<PlanetDTO> findAllPaged(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        Page<PlanetEntity> page = planetRepository.findAll(pageable);
        return page.get().map(planetEntity -> {
            PlanetDTO planetDTO = new PlanetDTO();
            BeanUtils.copyProperties(planetEntity, planetDTO);
            return planetDTO;
        });
    }

    public Optional<PlanetDTO> findById(Long id) {
        Optional<PlanetEntity> entity = planetRepository.findById(id);
        return entity.flatMap(this::convertToDTO);
    }

    public Optional<PlanetDTO> findByName(String name) {
        Optional<PlanetEntity> entity = planetRepository.findByName(name);
        return entity.flatMap(this::convertToDTO);
    }

    private Optional<PlanetDTO> convertToDTO(PlanetEntity entity) {
        PlanetDTO convertedPlanet = new PlanetDTO();
        BeanUtils.copyProperties(entity, convertedPlanet);
        return Optional.of(convertedPlanet);
    }

    @Transactional
    public void delete(Long id) {
        planetRepository.deleteById(id);
    }

}
