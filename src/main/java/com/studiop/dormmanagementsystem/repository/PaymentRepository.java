package com.studiop.dormmanagementsystem.repository;

import com.studiop.dormmanagementsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
