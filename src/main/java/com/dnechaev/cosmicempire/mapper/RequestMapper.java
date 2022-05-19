package com.dnechaev.cosmicempire.mapper;

import com.dnechaev.cosmicempire.dto.request.LordDtoRequest;
import com.dnechaev.cosmicempire.dto.request.PlanetDtoRequest;
import com.dnechaev.cosmicempire.repository.entity.LordEntity;
import com.dnechaev.cosmicempire.repository.entity.PlanetEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestMapper {
    PlanetEntity mapPlanet(PlanetDtoRequest planetEntity);
    LordEntity mapLord(LordDtoRequest lordEntity);
}
