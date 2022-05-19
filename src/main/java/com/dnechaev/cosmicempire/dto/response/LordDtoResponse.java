package com.dnechaev.cosmicempire.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class LordDtoResponse {
    private String name;
    private Integer age;
    List<PlanetDtoResponse> planets;
}
