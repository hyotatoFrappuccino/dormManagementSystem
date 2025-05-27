package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Admin;
import com.studiop.dormmanagementsystem.entity.dto.AdminDto;
import com.studiop.dormmanagementsystem.entity.dto.AdminRequest;
import com.studiop.dormmanagementsystem.exception.EntityException;
import com.studiop.dormmanagementsystem.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.studiop.dormmanagementsystem.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public Admin getById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new EntityException(RESOURCE_NOT_FOUND));
    }

    public AdminDto getDtoById(Long id) {
        return AdminDto.fromEntity(getById(id));
    }

    public List<AdminDto> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(AdminDto::fromEntity)
                .toList();
    }

    @Transactional
    public Admin addAdmin(AdminRequest request) {
        Admin admin = Admin.builder()
                .name(request.getName())
                .email(request.getEmail())
                .role(request.getRole())
                .build();

        return adminRepository.save(admin);
    }

    @Transactional
    public void editAdmin(Long id, AdminRequest request) {
        Admin admin = getById(id);

        admin.changeName(request.getName());
        admin.changeEmail(request.getEmail());
        admin.changeRole(request.getRole());
    }

    @Transactional
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new EntityException(RESOURCE_NOT_FOUND);
        }

        adminRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllAdmins() {
        adminRepository.deleteAllInBatch();
    }
}