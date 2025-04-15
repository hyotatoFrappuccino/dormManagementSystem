package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Business;
import com.studiop.dormmanagementsystem.repository.BusinessRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;

    public List<Business> getAllBusiness() {
        return businessRepository.findAll();
    }

    public Business findById(Long id) {
        return businessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 사업을 찾을 수 없습니다: " + id));
    }

    @Transactional
    public void addBusiness(String name) {
        businessRepository.save(new Business(name));
    }

    @Transactional
    public void removeBusiness(Long id) {
        businessRepository.deleteById(id);
    }
}