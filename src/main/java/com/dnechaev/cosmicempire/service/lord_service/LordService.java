package com.dnechaev.cosmicempire.service.lord_service;

import com.dnechaev.cosmicempire.dto.request.LordDtoRequest;
import com.dnechaev.cosmicempire.dto.response.LordDtoResponse;

import java.util.List;

public interface LordService {
    void createLord(LordDtoRequest lord) throws IllegalArgumentException;
    List<LordDtoResponse> getYoungestLords();
    List<LordDtoResponse> getLazyLords();
    void setLordToRule(String lordName, String planetName) throws IllegalArgumentException;
}
