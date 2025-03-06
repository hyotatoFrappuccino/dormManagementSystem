package com.studiop.dormmanagementsystem.repository;

import com.studiop.dormmanagementsystem.dto.BuildingDto;
import com.studiop.dormmanagementsystem.entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}
