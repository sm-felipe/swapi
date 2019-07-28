package com.ame.swapi.repository;

import com.ame.swapi.model.PlanetEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends CrudRepository<PlanetEntity, Long> {

    Optional<PlanetEntity> findByName(String name);

}
