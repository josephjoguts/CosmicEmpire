package com.dnechaev.cosmicempire.controller;

import com.dnechaev.cosmicempire.dto.request.LordDtoRequest;
import com.dnechaev.cosmicempire.dto.response.LordDtoResponse;
import com.dnechaev.cosmicempire.service.lord_service.LordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lords")
@RequiredArgsConstructor
public class LordController {
    final LordService lordService;

    @PostMapping
    public ResponseEntity<String> createLord(@RequestBody LordDtoRequest lord) {
        try {
            lordService.createLord(lord);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Lord " + lord.getName() + " created");

    }
    @GetMapping("/youngest")
    public ResponseEntity<List<LordDtoResponse>> getTopYoungestLords() {
        List<LordDtoResponse> lordDtoResponses = lordService.getYoungestLords();
        return ResponseEntity.ok(lordDtoResponses);
    }
    @GetMapping("/lazy")
    public ResponseEntity<List<LordDtoResponse>> getLazyLords() {
        List<LordDtoResponse> lazyLords = lordService.getLazyLords();
        return  ResponseEntity.ok(lazyLords);
    }
    @PatchMapping("/ruler/{lordName}/{planetName}")
    public ResponseEntity<String> setLordToRule(@PathVariable String lordName, @PathVariable String planetName) {
        try {
            lordService.setLordToRule(lordName, planetName);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Lord " + lordName + " is ruler of planet " + planetName);
    }

}
