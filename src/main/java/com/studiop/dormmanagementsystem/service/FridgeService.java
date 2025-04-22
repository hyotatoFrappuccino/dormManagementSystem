package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.*;
import com.studiop.dormmanagementsystem.entity.dto.FridgeApplicationDto;
import com.studiop.dormmanagementsystem.entity.dto.FridgeApplyRequest;
import com.studiop.dormmanagementsystem.entity.dto.MemberInfoDto;
import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import com.studiop.dormmanagementsystem.repository.FridgeApplicationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FridgeService {

    private final PaymentService paymentService;
    private final SurveyService surveyService;
    private final MemberService memberService;
    private final RoundService roundService;
    private final FridgeApplicationRepository fridgeApplicationRepository;

    public MemberInfoDto getMemberInfo(String studentId) {
        List<Survey> surveys = surveyService.getSurveys(studentId);
        PaymentStatus paymentStatus = paymentService.getPaymentStatus(studentId);

        return surveys.stream()
                .filter(Survey::isAgreed)
                .reduce((first, second) -> second)  // 마지막 요소 찾기
                .map(survey -> new MemberInfoDto(
                        survey.getName(),
                        paymentStatus,
                        true,
                        survey.getBuilding().getName(),
                        survey.getRoomNumber()
                ))
                .orElse(new MemberInfoDto("-", paymentStatus, null, "-", "-"));
    }

    public List<FridgeApplicationDto> getFridgeByMemberInfo(String studentId) {
        return memberService.findByStudentId(studentId)
                .map(Member::getFridgeApplications)
                .orElse(Collections.emptyList())
                .stream()
                .map(FridgeApplicationDto::fromEntity)
                .collect(Collectors.toList());
    }

    public List<FridgeApplication> getAllFridgeApplications() {
        return fridgeApplicationRepository.findAll();
    }

    @Transactional
    public void apply(FridgeApplyRequest request) {
        String studentId = request.getStudentId();
        if (!isAvailableRequest(studentId)) {
            throw new IllegalStateException("신청 요건이 충족되지 않았습니다. 납부 또는 서약서 상태를 확인해주세요.");
        }

        List<Survey> surveys = surveyService.getSurveys(studentId);

        Survey survey = surveys.stream()
                .reduce((first, second) -> second)
                .filter(Survey::isAgreed)
                .orElseThrow(() -> new IllegalStateException("동의한 서약서 정보 없음"));

        Member member = memberService.findByStudentId(studentId)
                .orElseGet(() -> memberService.addMember(
                        Member.builder()
                                .studentId(studentId)
                                .name(survey.getName())
                                .phone(survey.getPhoneNumber())
                                .building(survey.getBuilding())
                                .roomNumber(survey.getRoomNumber())
                                .paymentStatus(PaymentStatus.PAID)
                                .build()
                ));

        Round round = roundService.getById(request.getRoundId());

        // 해당 회차에 기존 신청이 있다면 삭제
        member.getFridgeApplications()
                .stream()
                .filter(app -> app.getRound().equals(round))
                .findFirst()
                .ifPresent(existingApplication -> {
                    member.removeFridgeApplication(existingApplication);
                    fridgeApplicationRepository.delete(existingApplication);
                });

        // 새로운 신청 추가
        FridgeApplication application = FridgeApplication.builder()
                .member(member)
                .round(round)
                .type(request.getType())
                .build();
        member.addFridgeApplication(application);
        fridgeApplicationRepository.save(application);
    }

    private boolean isAvailableRequest(String studentId) {
        List<Survey> surveys = surveyService.getSurveys(studentId);

        for (int i = surveys.size() - 1; i >= 0; i--) {
            if (surveys.get(i).isAgreed()) {
                return paymentService.getPaymentStatus(studentId) == PaymentStatus.PAID;
            }
        }

        return false;
    }

    @Transactional
    public void deleteFridgeApplication(Long id) {
        FridgeApplication application = fridgeApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 냉장고 신청 내역을 찾을 수 없습니다: " + id));

        Member member = application.getMember();
        member.getFridgeApplications().remove(application);

        fridgeApplicationRepository.delete(application);

        if (member.getFridgeApplications().isEmpty()) {
            memberService.deleteMemberById(member.getId());
        }
    }
}