package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Payment;
import com.studiop.dormmanagementsystem.entity.dto.PaymentDto;
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
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment addPayment(PaymentRequest request) {
        Payment payment = new Payment(request.getName(), request.getAmount(), request.getDate(), PaymentStatus.PAID, request.getType());
        return paymentRepository.save(payment);
    }

    public List<PaymentDto> getAllPayments() {
        return paymentRepository.findAll().stream().map(PaymentDto::fromEntity).collect(Collectors.toList());
    }

    public Long getNumOfTotalPayers() {
        return paymentRepository.count();
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 납부자를 찾을 수 없습니다: " + id));
    }

    public PaymentDto findDtoById(Long id) {
        return PaymentDto.fromEntity(findById(id));
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

    public PaymentStatus getPaymentStatus(String studentId) {
        // 납부 -> 환불 -> 미납 순서로 확인해야 하기때문에 2번 순회
        List<Payment> list = paymentRepository.findAllByStudentId(studentId);

        if (list.stream().anyMatch(p -> p.getStatus() == PaymentStatus.PAID)) {
            return PaymentStatus.PAID;
        }

        if (list.stream().anyMatch(p -> p.getStatus() == PaymentStatus.REFUNDED)) {
            return PaymentStatus.REFUNDED;
        }

        return PaymentStatus.NONE;
    }
}
