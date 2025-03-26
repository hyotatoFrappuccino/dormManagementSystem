package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.dto.BuildingDto;
import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import com.studiop.dormmanagementsystem.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;

    public Building addBuilding(String name, int fridgeSlots, int freezerSlots, int integratedSlots, FridgeType type) {
        return buildingRepository.save(new Building(name, fridgeSlots, freezerSlots, integratedSlots, type));
    }

    public List<Building> getAllBuildingsEntity() {
        return buildingRepository.findAll();
    }

    public List<BuildingDto> getAllBuildings() {
        List<Building> buildings = buildingRepository.findAll();
        return buildings.stream()
                .map(BuildingDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void editBuilding(Long id, String buildingName, int fridgeSlots, int freezerSlots, int integratedSlots, FridgeType type) {
        Building building = buildingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Building not found with id: " + id));

        building.changeName(buildingName);
        building.changeFridgeSlots(fridgeSlots);
        building.changeFreezerSlots(freezerSlots);
        building.changeIntegratedSlots(integratedSlots);
        building.changeType(type);
    }

    @Transactional
    public void deleteBuilding(Long id) {
        buildingRepository.deleteById(id);
    }
}
