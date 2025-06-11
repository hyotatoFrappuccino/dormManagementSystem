package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Admin;
import com.studiop.dormmanagementsystem.entity.dto.AdminDto;
import com.studiop.dormmanagementsystem.entity.dto.AdminRequest;
import com.studiop.dormmanagementsystem.entity.enums.Role;
import com.studiop.dormmanagementsystem.exception.EntityException;
import com.studiop.dormmanagementsystem.repository.AdminRepository;
import com.studiop.dormmanagementsystem.security.TokenBlacklistService;
import com.studiop.dormmanagementsystem.security.TokenRepository;
import com.studiop.dormmanagementsystem.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.studiop.dormmanagementsystem.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;
    private final TokenBlacklistService tokenBlacklistService;

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
    public AdminDto addAdmin(AdminRequest request) {
        Admin admin = Admin.builder()
                .name(request.name())
                .email(request.email())
                .role(Role.getRole(request.role()))
                .build();

        Admin savedAdmin = adminRepository.save(admin);
        return AdminDto.fromEntity(savedAdmin);
    }

    @Transactional
    public void editAdmin(Long id, AdminRequest request) {
        Admin admin = getById(id);

        admin.changeName(request.name());
        admin.changeEmail(request.email());
        admin.changeRole(Role.getRole(request.role()));
    }

    @Transactional
    public void deleteAdmin(Long id) {
        Admin admin = getById(id);
        String memberKey = admin.getEmail();

        tokenRepository.findById(memberKey).ifPresent(token -> {
            tokenBlacklistService.addTokenToBlacklist(token.getAccessToken());
            tokenService.deleteRefreshToken(memberKey);
        });

        adminRepository.delete(admin);
    }
}