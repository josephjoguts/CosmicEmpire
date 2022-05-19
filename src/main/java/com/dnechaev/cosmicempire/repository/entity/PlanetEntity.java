package com.dnechaev.cosmicempire.repository.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "planet_table")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "lord_id")
    private LordEntity lord;

}
