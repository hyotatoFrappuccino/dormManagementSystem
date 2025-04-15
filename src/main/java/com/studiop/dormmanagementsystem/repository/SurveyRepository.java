package com.studiop.dormmanagementsystem.repository;

import com.studiop.dormmanagementsystem.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
    List<Survey> findByStudentId(String studentId);
}