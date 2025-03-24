package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.*;
import com.studiop.dormmanagementsystem.entity.dto.FridgeApplyRequest;
import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import com.studiop.dormmanagementsystem.repository.FridgeApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FridgeService {
    private final PaymentService paymentService;
    private final SurveyService surveyService;
    private final FridgeApplicationRepository fridgeApplicationRepository;
    private final MemberService memberService;
    private final RoundService roundService;

    public Map<String, Object> getMemberInfo(String studentId) {
        Optional<Survey> survey = surveyService.getSurvey(studentId);

        return Map.of(
                "name", survey.map(Survey::getName).orElse("-"),
                "isPaid", paymentService.isPaid(studentId),
                "isAgreed", survey.isPresent() ? survey.map(Survey::isAgreed) : "notSubmitted",
                "building", survey.flatMap(s -> Optional.ofNullable(s.getBuilding()).map(Building::getName)).orElse("-"),
                "roomNumber", survey.map(Survey::getRoomNumber).orElse("-")
        );
    }

    public Map<String, Object> getFridgeInfo(String studentId) {
        Optional<Member> member = memberService.findByStudentId(studentId);

        // Member가 존재하면 신청 내역 반환, 없으면 빈 리스트 반환
        List<FridgeApplication> applications = member
                .map(Member::getFridgeApplications)
                .orElse(Collections.emptyList());

        Map<String, Object> response = new HashMap<>();
        response.put("list", applications);
        return response;
    }

    @Transactional
    public void apply(FridgeApplyRequest request) {
        String studentId = request.getStudentId();
        Survey survey = surveyService.getSurvey(studentId)
                .orElseThrow(() -> new IllegalStateException("서약서 정보 없음"));

        // 멤버가 존재하지 않으면 새로 추가
        Member member = memberService.findByStudentId(studentId)
                .orElseGet(() -> memberService.addMember(new Member(
                        studentId, survey.getName(), survey.getPhoneNumber(),
                        survey.getBuilding(), survey.getRoomNumber(), PaymentStatus.PAID
                )));

        // 새로운 신청 내역 생성 및 저장
        FridgeApplication application = new FridgeApplication(roundService.getRoundById(request.getRoundId()), request.getType());
        member.addFridgeApplication(application);

        fridgeApplicationRepository.save(application);
    }

    private boolean isAvailableRequest(String studentId) {
        return surveyService.getSurvey(studentId).map(Survey::isAgreed).orElse(false) && paymentService.isPaid(studentId);
    }
}
