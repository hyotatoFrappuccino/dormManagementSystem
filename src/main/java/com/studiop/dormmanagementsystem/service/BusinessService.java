package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Business;
import com.studiop.dormmanagementsystem.repository.BusinessRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;

    public Business getById(Long id) {
        return businessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 사업을 찾을 수 없습니다: " + id));
    }

    public List<Business> getAllBusiness() {
        return businessRepository.findAll();
    }

    @Transactional
    public void addBusiness(String name) {
        businessRepository.save(new Business(name));
    }

    @Transactional
    public void removeBusiness(Long id) {
        if (!businessRepository.existsById(id)) {
            throw new EntityNotFoundException("해당 ID의 사업을 찾을 수 없습니다: " + id);
        }
        businessRepository.deleteById(id);
    }
}