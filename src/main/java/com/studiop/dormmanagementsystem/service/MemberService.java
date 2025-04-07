package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Member;
import com.studiop.dormmanagementsystem.entity.dto.MemberFridgeApplicationResponse;
import com.studiop.dormmanagementsystem.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<MemberFridgeApplicationResponse> getAllMemberWithApplications() {
        List<Member> members = memberRepository.findAllWithFridgeApplications(); // fetch join 추천
        return members.stream()
                .map(MemberFridgeApplicationResponse::fromEntity)
                .toList();
    }

    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    public Optional<Member> findByStudentId(String studentId) {
        return memberRepository.findByStudentId(studentId);
    }

    public int getWarningCount(Long id) {
        return memberRepository.findById(id)
                .map(Member::getWarningCount)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
    }

    @Transactional
    public void increaseWarning(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
        member.increaseWarningCount();
    }

    @Transactional
    public void decreaseWarning(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
        member.decreaseWarningCount();
    }

}
