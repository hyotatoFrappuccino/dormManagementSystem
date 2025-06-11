package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Round;
import com.studiop.dormmanagementsystem.entity.dto.RoundDto;
import com.studiop.dormmanagementsystem.entity.dto.RoundRequest;
import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import com.studiop.dormmanagementsystem.service.RoundService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/rounds")
@RequiredArgsConstructor
public class RoundController {

    private final RoundService roundService;

    @Operation(summary = "회차 목록 조회")
    @GetMapping
    public ResponseEntity<List<RoundDto>> getAllRounds() {
        List<RoundDto> rounds = roundService.getAllRounds();
        return ResponseEntity.ok(rounds);
    }

    @Operation(summary = "회차 조회")
    @GetMapping("/{id}")
    public ResponseEntity<RoundDto> getRoundById(@PathVariable Long id) {
        RoundDto round = roundService.getDtoById(id);
        return ResponseEntity.ok(round);
    }

    @Operation(summary = "해당 회차 건물별 이용자 수 조회")
    @GetMapping("/fridgeApplications/{id}")
    public ResponseEntity<Map<Long, Map<FridgeType, Integer>>> getFridgeApplicationsByRoundId(@PathVariable Long id) {
        return ResponseEntity.ok(roundService.getFridgeCountByBuilding(id));
    }

    @Operation(summary = "회차 추가")
    @PostMapping
    public ResponseEntity<RoundDto> addRounds(final @RequestBody @Valid RoundRequest request) {
        Round addedRound = roundService.addRound(request);
        URI location = URI.create("/api/v1/rounds/" + addedRound.getId());
        return ResponseEntity.created(location).body(RoundDto.fromEntity(addedRound));
    }

    @Operation(summary = "회차 수정")
    @PutMapping("/{id}")
    public ResponseEntity<Void> editRound(@PathVariable Long id,
                                          final @RequestBody @Valid RoundRequest request) {
        roundService.updateRound(id, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "회차 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roundService.deleteRound(id);
        return ResponseEntity.noContent().build();
    }
}