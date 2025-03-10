package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.dto.BuildingDto;
import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.repository.BuildingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;

    public Building addBuilding(String name, int totalSlots) {
        return buildingRepository.save(new Building(name, totalSlots));
    }

    public Optional<Building> getBuildingById(Long id) {
        return buildingRepository.findById(id);
    }

    public List<BuildingDto> getAllBuildings() {
        return buildingRepository.findAll().stream()
                .map(BuildingDto::from)
                .collect(Collectors.toList());
    }

    public int getTotalSlots(Building building) {
        return buildingRepository.findById(building.getId())
                .map(Building::getTotalSlots)
                .orElseThrow(() -> new IllegalArgumentException("해당 건물을 찾을 수 없습니다. ID : " + building.getId()));
    }

    public int getTotalUsers(Building building) {
        return buildingRepository.findById(building.getId())
                .map(Building::getTotalUsers)
                .orElseThrow(() -> new IllegalArgumentException("해당 건물을 찾을 수 없습니다. ID : " + building.getId()));
    }

    @Transactional
    public int increaseUser(Building building) {
        return building.increaseUser();
    }

    @Transactional
    public int decreaseUser(Building building) {
        return building.decreaseUser();
    }

    @Transactional
    public int setUserSlots(Building building, int userSlots) {
        return building.setUserSlots(userSlots);
    }

    @Transactional
    public void changeName(Building building, String newName) {
        building.changeName(newName);
    }
}
