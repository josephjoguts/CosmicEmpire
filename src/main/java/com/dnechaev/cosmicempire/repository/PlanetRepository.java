package com.dnechaev.cosmicempire.repository;

import com.dnechaev.cosmicempire.repository.entity.PlanetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlanetRepository extends JpaRepository<PlanetEntity, Long> {
    Optional<PlanetEntity> findByName(String name);
}

