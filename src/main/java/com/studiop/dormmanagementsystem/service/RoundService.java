package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.FridgeApplication;
import com.studiop.dormmanagementsystem.entity.Round;
import com.studiop.dormmanagementsystem.entity.dto.RoundDto;
import com.studiop.dormmanagementsystem.entity.dto.RoundRequest;
import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import com.studiop.dormmanagementsystem.exception.EntityException;
import com.studiop.dormmanagementsystem.repository.RoundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.studiop.dormmanagementsystem.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;

    public Round getById(Long id) {
        return roundRepository.findById(id)
                .orElseThrow(() -> new EntityException(RESOURCE_NOT_FOUND));
    }

    public RoundDto getDtoById(Long id) {
        return RoundDto.fromEntity(getById(id));
    }

    public List<RoundDto> getAllRounds() {
        return roundRepository.findAll().stream().map(RoundDto::fromEntity).toList();
    }

    @Transactional
    public Round addRound(RoundRequest request) {
        if (isOverlapping(request.getStartDate(), request.getEndDate())) {
            throw new EntityException(DUPLICATE_ROUND);
        }
        Round round = Round.builder()
                .name(request.getName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .password(request.getPassword())
                .build();
        return roundRepository.save(round);
    }

    @Transactional
    public void updateRound(Long id, RoundRequest request) {
        Round round = getById(id);
        round.updateRound(request.getName(), request.getStartDate(), request.getEndDate(), request.getPassword());
    }

    @Transactional
    public void deleteRound(Long id) {
        roundRepository.delete(getById(id));
    }

    public Map<Long, Map<FridgeType, Integer>> getFridgeCountByBuilding(Long id) {
        List<FridgeApplication> fridgeApplications = getById(id).getFridgeApplications();

        // 결과를 반환할 Map. 각 건물에 대해 {FridgeType -> count} 형식으로 반환
        Map<Long, Map<FridgeType, Integer>> fridgeCountByBuilding = new HashMap<>();

        for (FridgeApplication fridgeApplication : fridgeApplications) {
            Building building = fridgeApplication.getBuilding();
            FridgeType type = fridgeApplication.getType();  // 냉장/냉동/통합 타입

            // 건물에 해당하는 카운트를 가져오거나 초기화
            fridgeCountByBuilding.putIfAbsent(building.getId(), new HashMap<>());
            Map<FridgeType, Integer> buildingFridgeCount = fridgeCountByBuilding.get(building.getId());

            // 'ALL' 타입일 경우 냉장, 냉동 슬롯 모두 업데이트
            if (type == FridgeType.ALL) {
                buildingFridgeCount.put(FridgeType.REFRIGERATOR, buildingFridgeCount.getOrDefault(FridgeType.REFRIGERATOR, 0) + 1);
                buildingFridgeCount.put(FridgeType.FREEZER, buildingFridgeCount.getOrDefault(FridgeType.FREEZER, 0) + 1);
            } else {
                // 해당 타입의 카운트를 업데이트
                buildingFridgeCount.put(type, buildingFridgeCount.getOrDefault(type, 0) + 1);
            }
        }
        return fridgeCountByBuilding;
    }

    private boolean isOverlapping(LocalDate startDate, LocalDate endDate) {
        return roundRepository.findAll().stream()
                .anyMatch(existing ->
                        !(endDate.isBefore(existing.getStartDate()) || startDate.isAfter(existing.getEndDate()))
                );
    }
}