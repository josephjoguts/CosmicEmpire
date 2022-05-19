package com.dnechaev.cosmicempire.integrated;

import com.dnechaev.cosmicempire.controller.PlanetController;
import com.dnechaev.cosmicempire.repository.PlanetRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlanetTests {
    @Autowired
    private PlanetController planetController;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlanetRepository planetRepository;
    @Test
    void loadControllerFromContext() {
        assertNotNull(planetController);
    }
    JsonMapper mapper = new JsonMapper();
    Map<String, String> content = new HashMap<>();

    @BeforeEach
    void clearTable(){
        planetRepository.deleteAll();
        content.clear();
        content.put("name", "Earth");
    }

    @Test
    void createPlanet() throws Exception {
        String json = mapper.writeValueAsString(content);
        mockMvc.perform(post("/api/v1/planets").content(json).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Planet Earth created"));

    }
    @Test
    void duplicatePlanet() throws Exception {
        String json = mapper.writeValueAsString(content);
        mockMvc.perform(post("/api/v1/planets").content(json).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Planet Earth created"));
        mockMvc.perform(post("/api/v1/planets").content(json).contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Planet with name Earth already exists"));
    }
    @Test
    void multiplePlanets() throws Exception{
        String json = mapper.writeValueAsString(content);
        mockMvc.perform(post("/api/v1/planets").content(json).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Planet Earth created"));
        content.clear();
        content.put("name", "Mars");
        json = mapper.writeValueAsString(content);
        mockMvc.perform(post("/api/v1/planets").content(json).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Planet Mars created"));
        content.clear();
        content.put("name", "Jupiter");
        json = mapper.writeValueAsString(content);
        mockMvc.perform(post("/api/v1/planets").content(json).contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Planet Jupiter created"));
    }
    @Test
    void deletePlanet() throws Exception{
        String json = mapper.writeValueAsString(content);
        mockMvc.perform(post("/api/v1/planets").content(json).contentType("application/json"))
                .andExpect(status().isOk()).
                andExpect(MockMvcResultMatchers.content().string("Planet Earth created"));
        mockMvc.perform(delete("/api/v1/planets/Earth"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Planet Earth deleted"));
    }
    @Test
    void deleteNonExistingPlanet() throws Exception{
        mockMvc.perform(delete("/api/v1/planets/Earth"))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Planet with name Earth does not exist"));
    }


}
