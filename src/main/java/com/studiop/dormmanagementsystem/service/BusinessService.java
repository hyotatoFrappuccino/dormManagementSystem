package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Business;
import com.studiop.dormmanagementsystem.exception.EntityException;
import com.studiop.dormmanagementsystem.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.studiop.dormmanagementsystem.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusinessService {

    private final BusinessRepository businessRepository;

    public Business getById(Long id) {
        return businessRepository.findById(id)
                .orElseThrow(() -> new EntityException(RESOURCE_NOT_FOUND));
    }

    public List<Business> getAllBusiness() {
        return businessRepository.findAll();
    }

    @Transactional
    public Business addBusiness(String name) {
        Business business = Business.builder()
                .name(name)
                .build();

        return businessRepository.save(business);
    }

    @Transactional
    public void removeBusiness(Long id) {
        if (!businessRepository.existsById(id)) {
            throw new EntityException(RESOURCE_NOT_FOUND);
        }
        businessRepository.deleteById(id);
    }
}