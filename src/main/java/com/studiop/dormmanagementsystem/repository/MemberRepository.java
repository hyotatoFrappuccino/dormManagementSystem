package com.studiop.dormmanagementsystem.repository;

import com.studiop.dormmanagementsystem.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByStudentId(String studentId);

    @Query("SELECT DISTINCT m FROM Member m LEFT JOIN FETCH m.fridgeApplications fa LEFT JOIN FETCH fa.round r JOIN FETCH m.building")
    List<Member> findAllWithFridgeApplications();
}