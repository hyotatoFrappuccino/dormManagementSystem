package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.dto.AdminDto;
import com.studiop.dormmanagementsystem.entity.dto.AdminRequest;
import com.studiop.dormmanagementsystem.entity.dto.RoleDto;
import com.studiop.dormmanagementsystem.entity.enums.Role;
import com.studiop.dormmanagementsystem.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @Operation(summary = "관리자 목록 조회")
    @GetMapping
    public ResponseEntity<List<AdminDto>> getAllAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @Operation(summary = "관리자 조회")
    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getDtoById(id));
    }

    @Operation(summary = "역할 목록 조회")
    @GetMapping("/roles")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(
                Arrays.stream(Role.values())
                        .map(role -> new RoleDto(
                                role.getKey().replaceFirst("^ROLE_", ""),
                                role.getTitle()
                        ))
                        .toList()
        );
    }

    @Operation(summary = "관리자 추가")
    @PreAuthorize("hasRole('PRESIDENT')")
    @PostMapping
    public ResponseEntity<AdminDto> addAdmin(final @RequestBody @Valid AdminRequest request) {
        AdminDto savedAdmin = adminService.addAdmin(request);
        URI location = URI.create("/api/v1/admins/" + savedAdmin.getId());
        return ResponseEntity.created(location).body(savedAdmin);
    }

    @Operation(summary = "관리자 수정")
    @PreAuthorize("hasRole('PRESIDENT')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAdmin(@PathVariable Long id, final @RequestBody @Valid AdminRequest request) {
        adminService.editAdmin(id, request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "관리자 삭제")
    @PreAuthorize("hasRole('PRESIDENT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }
}