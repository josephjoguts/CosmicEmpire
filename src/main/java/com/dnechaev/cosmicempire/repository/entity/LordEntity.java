package com.dnechaev.cosmicempire.repository.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "lord_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer age;
    @OneToMany(mappedBy = "lord", cascade = CascadeType.PERSIST)
    List<PlanetEntity> planets = new ArrayList<>();
}
