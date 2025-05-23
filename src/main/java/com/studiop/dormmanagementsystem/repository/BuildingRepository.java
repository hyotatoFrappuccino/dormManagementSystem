package com.studiop.dormmanagementsystem.repository;

import com.studiop.dormmanagementsystem.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BuildingRepository extends JpaRepository<Building, Long> {
    Optional<Building> findByName(String name);
}