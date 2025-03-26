package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.*;
import com.studiop.dormmanagementsystem.entity.dto.FridgeApplicationDto;
import com.studiop.dormmanagementsystem.entity.dto.FridgeApplyRequest;
import com.studiop.dormmanagementsystem.entity.dto.MemberInfoDto;
import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import com.studiop.dormmanagementsystem.repository.FridgeApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FridgeService {
    private final PaymentService paymentService;
    private final SurveyService surveyService;
    private final FridgeApplicationRepository fridgeApplicationRepository;
    private final MemberService memberService;
    private final RoundService roundService;

    public MemberInfoDto getMemberInfo(String studentId) {
        Optional<Survey> survey = surveyService.getSurvey(studentId);

        return new MemberInfoDto(
                survey.map(Survey::getName).orElse("-"),
                paymentService.isPaid(studentId),
                survey.map(Survey::isAgreed).orElse(null),
                survey.flatMap(s -> Optional.ofNullable(s.getBuilding()).map(Building::getName)).orElse("-"),
                survey.map(Survey::getRoomNumber).orElse("-")
        );
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
            throw new IllegalStateException("신청 요건이 충족되지 않았습니다.");
        }

        Survey survey = surveyService.getSurvey(studentId)
                .orElseThrow(() -> new IllegalStateException("서약서 정보 없음"));

        Member member = memberService.findByStudentId(studentId)
                .orElseGet(() -> memberService.addMember(new Member(
                        studentId, survey.getName(), survey.getPhoneNumber(),
                        survey.getBuilding(), survey.getRoomNumber(), PaymentStatus.PAID
                )));

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
        FridgeApplication application = new FridgeApplication(member, round, request.getType());
        member.addFridgeApplication(application);
        fridgeApplicationRepository.save(application);
    }

    private boolean isAvailableRequest(String studentId) {
        return surveyService.getSurvey(studentId).map(Survey::isAgreed).orElse(false) && paymentService.isPaid(studentId);
    }
}
