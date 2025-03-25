package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Building;
import com.studiop.dormmanagementsystem.entity.FridgeApplication;
import com.studiop.dormmanagementsystem.entity.Round;
import com.studiop.dormmanagementsystem.entity.dto.RoundDto;
import com.studiop.dormmanagementsystem.entity.dto.RoundUpdateRequest;
import com.studiop.dormmanagementsystem.repository.RoundRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoundService {

    private final RoundRepository roundRepository;

    @Transactional
    public Round addRound(String name, LocalDate startDate, LocalDate endDate) {
        Round round = new Round(name, startDate, endDate);
        return roundRepository.save(round);
    }

    @Transactional
    public void deleteRound(Long id) {
        roundRepository.deleteById(id);
    }

    public List<RoundDto> getAllRounds() {
        List<Round> rounds = roundRepository.findAll();
        return rounds.stream()
                .map(round -> new RoundDto(
                        round.getId(),
                        round.getName(),
                        round.getStartDate(),
                        round.getEndDate()
                ))
                .collect(Collectors.toList());
    }

    public Round getRoundById(Long id) {
        return roundRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public Map<Long, Integer> getFridgeCountByBuilding(Long id) {
        List<FridgeApplication> fridgeApplications = getRoundById(id).getFridgeApplications();
        Map<Long, Integer> fridgeCount = new HashMap<>();
        for (FridgeApplication fridgeApplication : fridgeApplications) {
            Building building = fridgeApplication.getBuilding();
            fridgeCount.put(building.getId(), fridgeCount.getOrDefault(building.getId(), 0) + 1);
        }
        return fridgeCount;
    }

    @Transactional
    public void updateRound(Long id, RoundUpdateRequest request) {
        Round round = findById(id);
        round.updateRound(request.getName(), request.getStartDate(), request.getEndDate());
        roundRepository.save(round);
    }

    public Optional<Round> getCurrentRound() {
        LocalDate today = LocalDate.now();
        return roundRepository.findAll().stream()
                .filter(round -> !today.isBefore(round.getStartDate()) && !today.isAfter(round.getEndDate()))
                .findFirst();
    }

    public Round findById(Long id) {
        return roundRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 회차를 찾을 수 없습니다: " + id));
    }
}
