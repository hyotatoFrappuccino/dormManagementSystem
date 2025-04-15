package com.studiop.dormmanagementsystem.repository;

import com.studiop.dormmanagementsystem.entity.Business;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessRepository extends JpaRepository<Business, Long> {
}