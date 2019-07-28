package com.ame.swapi.repository;

import com.ame.swapi.entity.PlanetEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends CrudRepository<PlanetEntity, Long> {
}
