package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.repository.BuildingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public List<Building> getAllBuildings() {
        return buildingRepository.findAll();
    }

    public Building getByName(String name) {
        Optional<Building> byName = buildingRepository.findByName(name);
        return byName.orElseThrow(() -> new EntityNotFoundException("\"" + name + "\"이름의 건물명을 찾을 수 없습니다. 서약서 새로고침 중 이 문제가 발생했다면, 서약서 양식에 따라 설문지를 제작해주세요."));
    }

    @Transactional
    public void increaseUser(Building building) {
        building.increaseUser();
    }

    @Transactional
    public void decreaseUser(Building building) {
        building.decreaseUser();
    }

    @Transactional
    public void setUserSlots(Building building, int userSlots) {
        building.setTotalSlots(userSlots);
    }

    @Transactional
    public void changeName(Building building, String newName) {
        building.changeName(newName);
    }

    @Transactional
    public void editBuilding(Long id, String buildingName, int userSlots) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Building not found with id: " + id));

        building.changeName(buildingName);
        building.setTotalSlots(userSlots);
    }

    @Transactional
    public void deleteBuilding(Long id) {
        buildingRepository.deleteById(id);
    }
}
