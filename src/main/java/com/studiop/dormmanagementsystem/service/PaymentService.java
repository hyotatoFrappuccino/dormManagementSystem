package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Member;
import com.studiop.dormmanagementsystem.entity.Payment;
import com.studiop.dormmanagementsystem.entity.dto.PaymentRequest;
import com.studiop.dormmanagementsystem.entity.dto.PaymentUpdateRequest;
import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import com.studiop.dormmanagementsystem.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberService memberService;

    @Transactional
    public Payment addPayment(PaymentRequest request) {
        // 입금자명(학번)으로 멤버 조회. 학번이 아닌 이름으로 입금했을 경우에는 null 반환, name 에 이름 삽입
        Member member = memberService.findByStudentId(request.getName()).orElse(null);
        String name = (member != null) ? member.getName() : request.getName();

        if (member != null) {
            member.setPaid();
        }

        Payment payment = new Payment(member, name, request.getAmount(), request.getDate(), PaymentStatus.PAID, request.getType());
        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Long getNumOfTotalPayers() {
        return paymentRepository.count();
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 납부자를 찾을 수 없습니다: " + id));
    }

    @Transactional
    public void updatePayment(Long id, PaymentUpdateRequest request) {
        Payment payment = findById(id);
        payment.updatePayment(request.getName(), request.getAmount(), request.getDate(), request.getStatus(), request.getType());
    }

    @Transactional
    public void deletePayment(Long id) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("해당 ID의 납부자를 찾을 수 없습니다: " + id);
        }
        paymentRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllPayments() {
        paymentRepository.deleteAllInBatch();
    }

    public boolean isPaid(String studentId) {
        return paymentRepository.existsByNameAndStatus(studentId, PaymentStatus.PAID);
    }
}
