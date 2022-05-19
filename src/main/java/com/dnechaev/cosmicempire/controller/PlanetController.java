package com.dnechaev.cosmicempire.controller;

import com.dnechaev.cosmicempire.dto.request.PlanetDtoRequest;
import com.dnechaev.cosmicempire.service.planet_service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/planets")
@RequiredArgsConstructor
public class PlanetController {
    final PlanetService planetService;
    @PostMapping
    public ResponseEntity<String> createPlanet(@RequestBody PlanetDtoRequest planet) {
        try {
            planetService.createPlanet(planet);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Planet " + planet.getName() + " created");
    }
    @DeleteMapping("/{planetName}")
    public ResponseEntity<String> deletePlanet(@PathVariable String planetName) {
        try {
            planetService.deletePlanet(planetName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Planet " + planetName + " deleted");
    }
}
