package com.dnechaev.cosmicempire.unit.service.lord_service;

import com.dnechaev.cosmicempire.dto.request.LordDtoRequest;
import com.dnechaev.cosmicempire.dto.response.LordDtoResponse;
import com.dnechaev.cosmicempire.mapper.RequestMapper;
import com.dnechaev.cosmicempire.mapper.ResponseMapper;
import com.dnechaev.cosmicempire.mapper.ResponseMapperImpl;
import com.dnechaev.cosmicempire.repository.LordRepository;
import com.dnechaev.cosmicempire.repository.PlanetRepository;
import com.dnechaev.cosmicempire.repository.entity.LordEntity;
import com.dnechaev.cosmicempire.repository.entity.PlanetEntity;
import com.dnechaev.cosmicempire.service.lord_service.LordServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LordServiceImplTest {
    @Mock
    private LordRepository lordRepository;
    @Mock
    private PlanetRepository planetRepository;
    @Mock
    private RequestMapper requestMapper;
    @Mock
    private ResponseMapperImpl responseMapper;



    @InjectMocks
    private LordServiceImpl lordService;

    @Test
    void createLord() {
        LordDtoRequest lord = new LordDtoRequest();
        lord.setName("Lord");
        lord.setAge(18);
        when(lordRepository.findByName("Lord")).thenReturn(Optional.empty());
        when(requestMapper.mapLord(lord)).thenReturn(new com.dnechaev.cosmicempire.repository.entity.LordEntity(null, "Lord", 18, null));
        when(lordRepository.save(Mockito.any(LordEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        lordService.createLord(lord);

        verify(lordRepository, times(1)).findByName("Lord");
        verify(requestMapper, times(1)).mapLord(lord);
        verify(lordRepository, times(1)).save(Mockito.any(LordEntity.class));
    }
    @Test
    void createLord_when_exists() {
        LordDtoRequest lord = new LordDtoRequest();
        lord.setName("Lord");
        lord.setAge(18);
        when(lordRepository.findByName("Lord")).thenReturn(Optional.of(new LordEntity(null, "Lord", 18, null)));

        assertThrows(IllegalArgumentException.class, () -> lordService.createLord(lord));
        verify(lordRepository, times(1)).findByName("Lord");
    }

    @Test
    void getYoungestLords() {
        List<LordEntity> lords = new ArrayList<>();
        lords.add(new LordEntity(null, "Lord1", 18, null));
        lords.add(new LordEntity(null, "Lord2", 19, null));
        lords.add(new LordEntity(null, "Lord3", 20, null));

        List<LordDtoResponse> expected = new ArrayList<>();
        expected.add(new LordDtoResponse("Lord1", 18, Collections.emptyList()));
        expected.add(new LordDtoResponse("Lord2", 19, Collections.emptyList()));
        expected.add(new LordDtoResponse("Lord3", 20, Collections.emptyList()));

        when(lordRepository.findYoungestLords()).thenReturn(lords);
        when(responseMapper.mapLord(Mockito.any(LordEntity.class))).thenCallRealMethod();
        List<LordDtoResponse> result = lordService.getYoungestLords();

        assertEquals(expected, result);
        verify(lordRepository, times(1)).findYoungestLords();
        verify(responseMapper, times(3)).mapLord(Mockito.any(LordEntity.class));


    }

    @Test
    void getLazyLords() {
        List<LordEntity> lords = new ArrayList<>();
        lords.add(new LordEntity(null, "Lord1", 18, null));
        lords.add(new LordEntity(null, "Lord2", 19, null));
        lords.add(new LordEntity(null, "Lord3", 20, null));

        List<LordDtoResponse> expected = new ArrayList<>();
        expected.add(new LordDtoResponse("Lord1", 18, Collections.emptyList()));
        expected.add(new LordDtoResponse("Lord2", 19, Collections.emptyList()));
        expected.add(new LordDtoResponse("Lord3", 20, Collections.emptyList()));

        when(lordRepository.findLazyLords()).thenReturn(lords);
        when(responseMapper.mapLord(Mockito.any(LordEntity.class))).thenCallRealMethod();
        List<LordDtoResponse> result = lordService.getLazyLords();

        assertEquals(expected, result);
        verify(lordRepository, times(1)).findLazyLords();
        verify(responseMapper, times(3)).mapLord(Mockito.any(LordEntity.class));

    }

    @Test
    void setLordToRule() {
        String lordName = "Lord";
        String planetName = "Earth";
        LordEntity lord = new LordEntity(1L, lordName, 18, null);
        PlanetEntity planet = new PlanetEntity(1L, planetName, null);
        when(lordRepository.findByName(lordName)).thenReturn(Optional.of(lord));
        when(planetRepository.findByName(planetName)).thenReturn(Optional.of(planet));
        when(planetRepository.save(Mockito.any(PlanetEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        lordService.setLordToRule(lordName, planetName);

        verify(lordRepository, times(1)).findByName(lordName);
        verify(planetRepository, times(1)).findByName(planetName);
        verify(planetRepository, times(1)).save(Mockito.any(PlanetEntity.class));
    }
    @Test
    void setLordToRule_when_lord_not_found() {
        String lordName = "Lord";
        String planetName = "Earth";
        when(lordRepository.findByName(lordName)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> lordService.setLordToRule(lordName, planetName));
        verify(lordRepository, times(1)).findByName(lordName);
        verify(planetRepository, times(0)).findByName(planetName);
        verify(planetRepository, times(0)).save(Mockito.any(PlanetEntity.class));
    }
    @Test
    void setLordToRule_when_planet_not_found() {
        String lordName = "Lord";
        String planetName = "Earth";
        LordEntity lord = new LordEntity(1L, lordName, 18, null);
        when(lordRepository.findByName(lordName)).thenReturn(Optional.of(lord));
        when(planetRepository.findByName(planetName)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> lordService.setLordToRule(lordName, planetName));
        verify(lordRepository, times(1)).findByName(lordName);
        verify(planetRepository, times(1)).findByName(planetName);
        verify(planetRepository, times(0)).save(Mockito.any(PlanetEntity.class));
    }

    @Test
    void setLordToRule_when_planet_is_already_ruled() {
        String lordName = "Lord";
        String planetName = "Earth";
        LordEntity lord = new LordEntity(1L, lordName, 18, null);
        PlanetEntity planet = new PlanetEntity(1L, planetName, lord);
        when(lordRepository.findByName(lordName)).thenReturn(Optional.of(lord));
        when(planetRepository.findByName(planetName)).thenReturn(Optional.of(planet));

        assertThrows(IllegalArgumentException.class, () -> lordService.setLordToRule(lordName, planetName));
        verify(lordRepository, times(1)).findByName(lordName);
        verify(planetRepository, times(1)).findByName(planetName);
        verify(planetRepository, times(0)).save(Mockito.any(PlanetEntity.class));
    }

}