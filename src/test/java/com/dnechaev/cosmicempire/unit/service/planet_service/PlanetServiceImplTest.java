package com.dnechaev.cosmicempire.unit.service.planet_service;

import com.dnechaev.cosmicempire.dto.request.PlanetDtoRequest;
import com.dnechaev.cosmicempire.mapper.RequestMapper;
import com.dnechaev.cosmicempire.repository.PlanetRepository;
import com.dnechaev.cosmicempire.repository.entity.PlanetEntity;
import com.dnechaev.cosmicempire.service.planet_service.PlanetServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanetServiceImplTest {
    @Mock
    private PlanetRepository planetRepository;
    @Mock
    private RequestMapper requestMapper;
    @InjectMocks
    private PlanetServiceImpl planetService;

    @Test
    void createPlanet() {
        PlanetDtoRequest planet = new PlanetDtoRequest();
        planet.setName("Earth");
        when(planetRepository.findByName("Earth")).thenReturn(Optional.empty());
        when(requestMapper.mapPlanet(planet)).thenReturn(new PlanetEntity(null,"Earth", null));
        when(planetRepository.save(Mockito.any(PlanetEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
        planetService.createPlanet(planet);

        verify(planetRepository, times(1)).findByName("Earth");
        verify(requestMapper, times(1)).mapPlanet(planet);
        verify(planetRepository, times(1)).save(Mockito.any(PlanetEntity.class));
    }
    @Test
    void createPlanet_when_exists() {
        PlanetDtoRequest planet = new PlanetDtoRequest();
        planet.setName("Earth");
        when(planetRepository.findByName("Earth")).thenReturn(Optional.of(new PlanetEntity(null,"Earth", null)));

        assertThrows(IllegalArgumentException.class, () -> planetService.createPlanet(planet));
        verify(planetRepository, times(1)).findByName("Earth");
    }
    @Test
    void deletePlanet() {
        when(planetRepository.findByName("Earth")).thenReturn(Optional.of(new PlanetEntity(null,"Earth", null)));
        doNothing().when(planetRepository).delete(Mockito.any(PlanetEntity.class));
        planetService.deletePlanet("Earth");

        verify(planetRepository, times(1)).findByName("Earth");
        verify(planetRepository, times(1)).delete(Mockito.any(PlanetEntity.class));
    }
    @Test
    void deletePlanet_when_not_exists() {
        when(planetRepository.findByName("Earth")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> planetService.deletePlanet("Earth"));

        verify(planetRepository, times(1)).findByName("Earth");
    }



}