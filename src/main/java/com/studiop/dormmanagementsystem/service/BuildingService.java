package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.dto.BuildingDto;
import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import com.studiop.dormmanagementsystem.repository.BuildingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;

    public Building getById(Long id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 건물 찾을 수 없습니다: " + id));
    }

    @Transactional
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
        Building building = getById(id);

        building.changeName(buildingName);
        building.changeFridgeSlots(fridgeSlots);
        building.changeFreezerSlots(freezerSlots);
        building.changeIntegratedSlots(integratedSlots);
        building.changeType(type);
    }

    @Transactional
    public void deleteBuilding(Long id) {
        if (!buildingRepository.existsById(id)) {
            throw new EntityNotFoundException("해당 ID의 건물을 찾을 수 없습니다: " + id);
        }
        buildingRepository.deleteById(id);
    }
}