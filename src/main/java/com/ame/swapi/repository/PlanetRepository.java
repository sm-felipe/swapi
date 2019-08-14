package com.ame.swapi.repository;

import com.ame.swapi.model.PlanetEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanetRepository extends PagingAndSortingRepository<PlanetEntity, Long> {

    Optional<PlanetEntity> findByName(String name);

    Page<PlanetEntity> findAll(Pageable pageable);


}
