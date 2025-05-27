package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Member;
import com.studiop.dormmanagementsystem.entity.dto.MemberFridgeApplicationResponse;
import com.studiop.dormmanagementsystem.exception.EntityException;
import com.studiop.dormmanagementsystem.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.studiop.dormmanagementsystem.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member getById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new EntityException(RESOURCE_NOT_FOUND));
    }

    public Optional<Member> findByStudentId(String studentId) {
        return memberRepository.findByStudentId(studentId);
    }

    public List<MemberFridgeApplicationResponse> getAllMemberWithApplications() {
        List<Member> members = memberRepository.findAllWithFridgeApplications();
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
            throw new EntityException(RESOURCE_NOT_FOUND);
        }
        memberRepository.deleteById(id);
    }

    // ====== 경고 ===== //

    public int getWarningCount(Long id) {
        return memberRepository.findById(id)
                .map(Member::getWarningCount)
                .orElseThrow(() -> new EntityException(RESOURCE_NOT_FOUND));
    }

    @Transactional
    public void increaseWarning(Long id) {
        Member member = getById(id);
        member.increaseWarningCount();
    }

    @Transactional
    public void decreaseWarning(Long id) {
        Member member = getById(id);
        member.decreaseWarningCount();
    }
}