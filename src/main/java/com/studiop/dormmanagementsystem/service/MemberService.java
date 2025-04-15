package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Member;
import com.studiop.dormmanagementsystem.entity.dto.MemberFridgeApplicationResponse;
import com.studiop.dormmanagementsystem.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Optional<Member> findByStudentId(String studentId) {
        return memberRepository.findByStudentId(studentId);
    }

    public List<MemberFridgeApplicationResponse> getAllMemberWithApplications() {
        List<Member> members = memberRepository.findAllWithFridgeApplications(); // fetch join 추천
        return members.stream()
                .map(MemberFridgeApplicationResponse::fromEntity)
                .toList();
    }

    @Transactional
    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    @Transactional
    public void deleteMemberById(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new EntityNotFoundException("해당 ID의 사용자를 찾을 수 없습니다: " + id);
        }
        memberRepository.deleteById(id);
    }

    // ====== 경고 ===== //

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