package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Admin;
import com.studiop.dormmanagementsystem.entity.dto.AdminDto;
import com.studiop.dormmanagementsystem.entity.dto.AdminRequest;
import com.studiop.dormmanagementsystem.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    @Transactional
    public Admin addAdmin(AdminRequest request) {
        Admin admin = new Admin(request.getName(), request.getEmail(), request.getRole());
        return adminRepository.save(admin);
    }

    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Transactional
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new EntityNotFoundException("해당 ID의 관리자를 찾을 수 없습니다: " + id);
        }
        adminRepository.deleteById(id);
    }

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public List<AdminDto> getAllAdmins() {
        return adminRepository.findAll().stream().map(AdminDto::fromEntity).collect(Collectors.toList());
    }

    public AdminDto findDtoById(Long id) {
        return AdminDto.fromEntity(findById(id));
    }

    public Admin findById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 관리자를 찾을 수 없습니다: " + id));
    }

    @Transactional
    public void deleteAllAdmins() {
        adminRepository.deleteAllInBatch();
    }

    @Transactional
    public void editAdmin(Long id, AdminDto request) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found with id: " + id));
        admin.changeName(request.getName());
        admin.changeEmail(request.getEmail());
        admin.changeRole(request.getRole());
    }
}
