package com.studiop.dormmanagementsystem.repository;

import com.studiop.dormmanagementsystem.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByStudentId(String studentId);
}
