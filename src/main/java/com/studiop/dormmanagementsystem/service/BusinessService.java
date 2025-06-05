package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Business;
import com.studiop.dormmanagementsystem.entity.dto.BusinessDto;
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

    public List<BusinessDto> getAllBusiness() {
        return businessRepository.findAll().stream().map(BusinessDto::fromEntity).toList();
    }

    @Transactional
    public BusinessDto addBusiness(String name) {
        Business business = Business.builder()
                .name(name)
                .build();

        Business savedBusiness = businessRepository.save(business);
        return BusinessDto.fromEntity(savedBusiness);
    }

    @Transactional
    public void removeBusiness(Long id) {
        businessRepository.delete(getById(id));
    }
}