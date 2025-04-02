package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Admin;
import com.studiop.dormmanagementsystem.entity.dto.AdminDto;
import com.studiop.dormmanagementsystem.entity.dto.AdminRequest;
import com.studiop.dormmanagementsystem.entity.dto.RoleDto;
import com.studiop.dormmanagementsystem.entity.enums.Role;
import com.studiop.dormmanagementsystem.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        List<AdminDto> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    @Operation(summary = "관리자 조회")
    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Long id) {
        AdminDto admin = adminService.findDtoById(id);
        return ResponseEntity.ok(admin);
    }

    @Operation(summary = "역할 조회")
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
    @PostMapping
    public ResponseEntity<AdminDto> addAdmin(@RequestBody AdminRequest request) {
        Admin savedAdmin = adminService.addAdmin(request);
        URI location = URI.create("/api/v1/admins/" + savedAdmin.getId());
        return ResponseEntity.created(location).body(AdminDto.fromEntity(savedAdmin));
    }

    @Operation(summary = "관리자 수정")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateAdmin(@PathVariable("id") Long id, @RequestBody AdminDto request) {
        adminService.editAdmin(id, request);
        return ResponseEntity.ok("OK");
    }

    @Operation(summary = "관리자삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "전체 관리자 삭제")
    @DeleteMapping
    public ResponseEntity<Void> deleteAllAdmins() {
        adminService.deleteAllAdmins();
        return ResponseEntity.noContent().build();
    }
}
