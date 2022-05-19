package com.dnechaev.cosmicempire.repository;

import com.dnechaev.cosmicempire.repository.entity.LordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LordRepository extends JpaRepository<LordEntity, Long> {

    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM lord_table " +
            "where age is not null" +
            " order by age asc limit 10")
    List<LordEntity> findYoungestLords();
    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM lord_table l " +
            "left outer join planet_table p on l.id = p.lord_id" +
            " where p.id is null")
    List<LordEntity> findLazyLords();
    Optional<LordEntity> findByName(String name);
    
}
