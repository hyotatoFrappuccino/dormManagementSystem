package com.studiop.dormmanagementsystem.repository;

import com.studiop.dormmanagementsystem.entity.Payment;
import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p.status FROM Payment p WHERE p.name = :studentId")
    PaymentStatus findStatusByName(String studentId);
    boolean existsByName(String studentId);
}
