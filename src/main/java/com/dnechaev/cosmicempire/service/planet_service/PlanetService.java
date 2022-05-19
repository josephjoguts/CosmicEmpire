package com.dnechaev.cosmicempire.service.planet_service;

import com.dnechaev.cosmicempire.dto.request.PlanetDtoRequest;

public interface PlanetService {
    void createPlanet(PlanetDtoRequest planet) throws IllegalArgumentException;
    void deletePlanet(String planetName) throws IllegalArgumentException;
}
