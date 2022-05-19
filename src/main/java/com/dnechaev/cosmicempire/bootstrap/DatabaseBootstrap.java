package com.dnechaev.cosmicempire.bootstrap;

import com.dnechaev.cosmicempire.repository.LordRepository;
import com.dnechaev.cosmicempire.repository.PlanetRepository;
import com.dnechaev.cosmicempire.repository.entity.LordEntity;
import com.dnechaev.cosmicempire.repository.entity.PlanetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseBootstrap implements CommandLineRunner {
    @Autowired
    PlanetRepository planetRepository;
    @Autowired
    LordRepository lordRepository;
    @Override
    public void run(String... args) throws Exception {
        PlanetEntity planet = PlanetEntity.builder().name("test1").build();
        PlanetEntity planet1 = PlanetEntity.builder().name("test2").build();
        LordEntity lord = LordEntity.builder().name("lord1").planets(new ArrayList<>()).build();
        planet.setLord(lord);
        planet1.setLord(lord);
        lord.getPlanets().add(planet);
        lord.getPlanets().add(planet1);
        lordRepository.save(lord);
        LordEntity lord1 = LordEntity.builder().name("lord2").build();
        lordRepository.save(lord1);


    }
}
