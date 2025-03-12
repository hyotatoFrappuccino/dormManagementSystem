package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Member;
import com.studiop.dormmanagementsystem.entity.Payment;
import com.studiop.dormmanagementsystem.entity.PaymentStatus;
import com.studiop.dormmanagementsystem.entity.PaymentType;
import com.studiop.dormmanagementsystem.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberService memberService;

    /**
     * 납부자 목록 추가
     * @param depositName 입금자명
     * @param amount 금액
     * @param paymentDate 입금일
     * @param paymentType 납부유형(BANK_TRANSFER, ON_SITE)
     */
    @Transactional
    public void addPayment(String depositName, int amount, LocalDate paymentDate, PaymentType paymentType) {
        // 입금자명(학번)으로 멤버 조회. 학번이 아닌 이름으로 입금했을 경우에는 null 반환, name 에 이름 삽입
        Optional<Member> memberOptional = memberService.findByStudentId(depositName);
        Member member = memberOptional.orElse(null);

        String name = (member == null) ? depositName : member.getName();

        // 서약서만 제출하고 미납부인 멤버 납부 상태 변경
        if (member != null) {
            member.setPaid();
        }
        paymentRepository.save(new Payment(member, name, amount, paymentDate, PaymentStatus.PAID, paymentType));
    }

    public List<Payment> getAllPayers() {
        return paymentRepository.findAll();
    }

    /**
     * @return 전체 납부자 수
     */
    public Long getTotalPayers() {
        return paymentRepository.count();
    }

    @Transactional
    public void editPayment(Long id, String depositName, int amount, LocalDate paymentDate, PaymentStatus paymentStatus, PaymentType paymentType) {
        Optional<Payment> paymentOptional = paymentRepository.findById(id);

        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.editPayment(depositName, amount, paymentDate, paymentStatus, paymentType);
        } else {
            throw new EntityNotFoundException("해당 ID의 Payment 를 찾을 수 없습니다." + id);
        }
    }

    @Transactional
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("해당 ID의 납부자를 찾을 수 없습니다: " + id);
        }
        paymentRepository.deleteById(id);
    }

}
