package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.dto.BuildingDto;
import com.studiop.dormmanagementsystem.entity.dto.BuildingRequest;
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
    public BuildingDto addBuilding(BuildingRequest request) {
        Building building = Building.builder()
                .name(request.name())
                .fridgeSlots(request.fridgeSlots())
                .freezerSlots(request.freezerSlots())
                .integratedSlots(request.integratedSlots())
                .type(FridgeType.getFridgeType(request.type()))
                .build();

        Building savedBuilding = buildingRepository.save(building);
        return BuildingDto.fromEntity(savedBuilding);
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
    public void editBuilding(Long id, BuildingRequest request) {
        Building building = getById(id);

        building.changeName(request.name());
        building.changeFridgeSlots(request.fridgeSlots());
        building.changeFreezerSlots(request.freezerSlots());
        building.changeIntegratedSlots(request.integratedSlots());
        building.changeType(FridgeType.getFridgeType(request.type()));
    }

    @Transactional
    public void deleteBuilding(Long id) {
        buildingRepository.delete(getById(id));
    }
}