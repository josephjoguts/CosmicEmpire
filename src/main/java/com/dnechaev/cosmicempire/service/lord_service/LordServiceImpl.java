package com.dnechaev.cosmicempire.service.lord_service;

import com.dnechaev.cosmicempire.dto.request.LordDtoRequest;
import com.dnechaev.cosmicempire.dto.response.LordDtoResponse;
import com.dnechaev.cosmicempire.mapper.RequestMapper;
import com.dnechaev.cosmicempire.mapper.ResponseMapper;
import com.dnechaev.cosmicempire.repository.LordRepository;
import com.dnechaev.cosmicempire.repository.PlanetRepository;
import com.dnechaev.cosmicempire.repository.entity.LordEntity;
import com.dnechaev.cosmicempire.repository.entity.PlanetEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LordServiceImpl implements LordService {
    final LordRepository lordRepository;
    final PlanetRepository planetRepository;
    final ResponseMapper responseMapper;
    final RequestMapper requestMapper;
    @Transactional
    @Override
    public void createLord(LordDtoRequest lord) {
        lordRepository.findByName(lord.getName()).ifPresent(
                entity -> {
                    throw new IllegalArgumentException("Lord with name " + entity.getName() + " already exists");
                }
        );
        LordEntity entity = requestMapper.mapLord(lord);
        entity.setPlanets(new ArrayList<>());
        lordRepository.save(entity);
    }

    @Override
    public List<LordDtoResponse> getYoungestLords() {
        List<LordEntity> youngestLords = lordRepository.findYoungestLords();
        List<LordDtoResponse> youngestLordsDto = youngestLords.stream().map(responseMapper::mapLord).collect(Collectors.toList());
        return youngestLordsDto;
    }

    @Override
    public List<LordDtoResponse> getLazyLords() {
        List<LordEntity> lazyLords = lordRepository.findLazyLords();
        List<LordDtoResponse> lazyLordsDto = lazyLords.stream().map(responseMapper::mapLord).collect(Collectors.toList());
        return lazyLordsDto;
    }

    @Override
    @Transactional
    public void setLordToRule(String lordName, String planetName) {
        LordEntity lord = lordRepository.findByName(lordName).orElseThrow(
                () -> new IllegalArgumentException("Lord with name " + lordName + " does not exist")
        );
        PlanetEntity planet = planetRepository.findByName(planetName).orElseThrow(
                () -> new IllegalArgumentException("Planet with name " + planetName + " does not exist")
        );
        if(planet.getLord() != null){
            throw new IllegalArgumentException("Planet with name " + planetName + " is already ruled by " + planet.getLord().getName());
        }
        planet.setLord(lord);
        planetRepository.save(planet);
    }
}
