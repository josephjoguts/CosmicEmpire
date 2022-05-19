package com.dnechaev.cosmicempire.service.planet_service;

import com.dnechaev.cosmicempire.dto.request.PlanetDtoRequest;
import com.dnechaev.cosmicempire.mapper.RequestMapper;
import com.dnechaev.cosmicempire.repository.PlanetRepository;
import com.dnechaev.cosmicempire.repository.entity.PlanetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlanetServiceImpl implements PlanetService {
    final PlanetRepository planetRepository;
    final RequestMapper requestMapper;

    @Override
    @Transactional
    public void createPlanet(PlanetDtoRequest planet) {
        planetRepository.findByName(planet.getName()).ifPresent(
                entity -> {
                    throw new IllegalArgumentException("Planet with name " + entity.getName() + " already exists");
                }
        );
        PlanetEntity planetEntity = requestMapper.mapPlanet(planet);
        planetRepository.save(planetEntity);

    }

    @Override
    public void deletePlanet(String planetName) {
        planetRepository.findByName(planetName).ifPresentOrElse(
                planetRepository::delete,
                () -> {
                    throw new IllegalArgumentException("Planet with name " + planetName + " does not exist");
                }
        );
    }
}
