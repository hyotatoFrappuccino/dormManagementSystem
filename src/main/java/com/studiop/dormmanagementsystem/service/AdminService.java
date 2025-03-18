package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Admin;
import com.studiop.dormmanagementsystem.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    @Transactional
    public void addAdmin(Admin admin) {
        adminRepository.save(admin);
    }

    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Transactional
    public void deleteAdmin(Admin admin) {
        adminRepository.delete(admin);
    }

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

}
