package com.ame.swapi.service;

import com.ame.swapi.model.PlanetEntity;
import com.ame.swapi.repository.PlanetRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlanetService {

    private final PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    @Transactional
    public void save() {
        //planetRepository.save()
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

    public Optional<PlanetEntity> findById(Long id) {
        return planetRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        planetRepository.deleteById(id);
    }

}
