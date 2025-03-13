package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Member;
import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import com.studiop.dormmanagementsystem.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member addMember(String studentId, String name, PaymentStatus paymentStatus) {
        return new Member(studentId, name, paymentStatus);
    }

    public Optional<Member> findByStudentId(String studentId) {
        return memberRepository.findByStudentId(studentId);
    }

}
