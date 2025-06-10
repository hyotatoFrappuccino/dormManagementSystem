package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.*;
import com.studiop.dormmanagementsystem.entity.dto.FridgeApplicationDto;
import com.studiop.dormmanagementsystem.entity.dto.FridgeApplyRequest;
import com.studiop.dormmanagementsystem.entity.dto.MemberInfoDto;
import com.studiop.dormmanagementsystem.entity.enums.FridgeType;
import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import com.studiop.dormmanagementsystem.exception.EntityException;
import com.studiop.dormmanagementsystem.repository.FridgeApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.studiop.dormmanagementsystem.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FridgeService {

    private final PaymentService paymentService;
    private final SurveyService surveyService;
    private final MemberService memberService;
    private final RoundService roundService;
    private final FridgeApplicationRepository fridgeApplicationRepository;

    // 냉장고 신청/연장 화면 신청자 정보 조회
    public MemberInfoDto getMemberInfo(String studentId) {
        List<Survey> surveys = surveyService.getSurveys(studentId);
        PaymentStatus paymentStatus = paymentService.getPaymentStatus(studentId);

        // 해당 학번의 동의한 서약서 중 가장 최근의 서약서를 골라 매핑, 납부 여부와 결합하여 반환
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
                // 동의한 서약서가 없는 경우 기본값 반환
                .orElse(new MemberInfoDto("-", paymentStatus, null, "-", "-"));
    }

    // 해당 학번의 냉장고 신청 내역 반환
    public List<FridgeApplicationDto> getFridgeByMemberInfo(String studentId) {
        return memberService.findByStudentId(studentId)
                .map(Member::getFridgeApplications)
                .orElse(Collections.emptyList())
                .stream()
                .map(FridgeApplicationDto::fromEntity)
                .toList();
    }

//    // 전체 냉장고 신청 내역 반환
//    public List<FridgeApplication> getAllFridgeApplications() {
//        return fridgeApplicationRepository.findAll();
//    }

    @Transactional
    public void apply(FridgeApplyRequest request) {
        String studentId = request.studentId();

        // 가장 최근의 동의한 서약서 가져오기
        Survey survey = getRecentAgreedSurvey(studentId);

        // 납부자인지 확인
        if (paymentService.getPaymentStatus(studentId) != PaymentStatus.PAID) {
            throw new EntityException(APPLICATION_CONDITION_NOT_MET);
        }

        // 기존 신청자라면 Member 엔티티 반환, 신규 신청자라면 새 Member 엔티티 생성 후 반환
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

        // 신청 회차
        Round round = roundService.getById(request.roundId());

        // 중복 신청 방지를 위해 해당 회차 기존 신청 내역 제거
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
                .building(survey.getBuilding())
                .round(round)
                .type(FridgeType.getFridgeType(request.type()))
                .appliedAt(LocalDateTime.now())
                .build();
        member.addFridgeApplication(application);
        fridgeApplicationRepository.save(application);
    }

    private Survey getRecentAgreedSurvey(String studentId) {
        List<Survey> surveys = surveyService.getSurveys(studentId);
        return surveys.stream()
                .reduce((first, second) -> second)
                .filter(Survey::isAgreed)
                .orElseThrow(() -> new EntityException(APPLICATION_CONDITION_NOT_MET));
    }

    @Transactional
    public void deleteFridgeApplication(Long id) {
        FridgeApplication application = fridgeApplicationRepository.findById(id)
                .orElseThrow(() -> new EntityException(RESOURCE_NOT_FOUND));

        Member member = application.getMember();
        member.getFridgeApplications().remove(application);

        fridgeApplicationRepository.delete(application);

        if (member.getFridgeApplications().isEmpty()) {
            memberService.deleteMemberById(member.getId());
        }
    }

    @Transactional
    public void deleteAllFridgeApplication() {
        fridgeApplicationRepository.deleteAllInBatch();
    }
}