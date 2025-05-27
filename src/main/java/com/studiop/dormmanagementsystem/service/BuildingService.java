package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.dto.BuildingDto;
import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import com.studiop.dormmanagementsystem.exception.EntityException;
import com.studiop.dormmanagementsystem.repository.BuildingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.studiop.dormmanagementsystem.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BuildingService {

    private final BuildingRepository buildingRepository;

    public Building getById(Long id) {
        return buildingRepository.findById(id)
                .orElseThrow(() -> new EntityException(RESOURCE_NOT_FOUND));
    }

    @Transactional
    public Building addBuilding(String name, int fridgeSlots, int freezerSlots, int integratedSlots, FridgeType type) {
        Building building = Building.builder()
                .name(name)
                .fridgeSlots(fridgeSlots)
                .freezerSlots(freezerSlots)
                .integratedSlots(integratedSlots)
                .type(type)
                .build();

        return buildingRepository.save(building);
    }

    public List<Building> getAllBuildingsEntity() {
        return buildingRepository.findAll();
    }

    public List<BuildingDto> getAllBuildings() {
        List<Building> buildings = buildingRepository.findAll();

        return buildings.stream()
                .map(BuildingDto::fromEntity)
                .toList();
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
            throw new EntityException(RESOURCE_NOT_FOUND);
        }

        buildingRepository.deleteById(id);
    }
}