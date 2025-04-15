package com.studiop.dormmanagementsystem.repository;

import com.studiop.dormmanagementsystem.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByStudentId(String studentId);

    @Query("SELECT s FROM Payment s JOIN FETCH s.participations")
    List<Payment> findAllWithParticipations();
}