package com.studiop.dormmanagementsystem.repository;

import com.studiop.dormmanagementsystem.entity.Payment;
import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByNameAndStatus(String studentId, PaymentStatus status);
}
