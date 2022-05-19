package com.dnechaev.cosmicempire.mapper;

import com.dnechaev.cosmicempire.dto.response.LordDtoResponse;
import com.dnechaev.cosmicempire.dto.response.PlanetDtoResponse;
import com.dnechaev.cosmicempire.repository.entity.LordEntity;
import com.dnechaev.cosmicempire.repository.entity.PlanetEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResponseMapper {
    PlanetDtoResponse mapPlanet(PlanetEntity planetDtoRequest);
    LordDtoResponse mapLord(LordEntity planetDtoRequest);
}
