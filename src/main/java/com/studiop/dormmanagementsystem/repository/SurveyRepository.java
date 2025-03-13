package com.studiop.dormmanagementsystem.repository;

import com.studiop.dormmanagementsystem.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    boolean existsByStudentIdAndAgreedIsTrue(String studentId);
    Optional<Survey> findByStudentId(String studentId);
}
