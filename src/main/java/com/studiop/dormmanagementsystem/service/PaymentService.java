package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.Business;
import com.studiop.dormmanagementsystem.entity.Payment;
import com.studiop.dormmanagementsystem.entity.PaymentBusinessParticipation;
import com.studiop.dormmanagementsystem.entity.dto.PaymentDto;
import com.studiop.dormmanagementsystem.entity.dto.PaymentRequest;
import com.studiop.dormmanagementsystem.entity.dto.PaymentUpdateRequest;
import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import com.studiop.dormmanagementsystem.entity.enums.PaymentType;
import com.studiop.dormmanagementsystem.exception.EntityException;
import com.studiop.dormmanagementsystem.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.studiop.dormmanagementsystem.exception.ErrorCode.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BusinessService businessService;

    public Payment getById(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityException(RESOURCE_NOT_FOUND));
    }

    public PaymentDto getDtoById(Long id) {
        return PaymentDto.fromEntity(getById(id));
    }

    public List<PaymentDto> getAllPayments() {
        List<Payment> payments = paymentRepository.findAllWithParticipations();
        return payments.stream()
                .map(PaymentDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Long getNumOfTotalPayers() {
        return paymentRepository.count();
    }

    @Transactional
    public Payment addPayment(PaymentRequest request) {
        Payment payment = Payment.builder()
                .studentId(request.name())
                .amount(request.amount())
                .date(request.date())
                .status(PaymentStatus.PAID)
                .type(PaymentType.getPaymentType(request.type()))
                .build();
        return paymentRepository.save(payment);
    }

    @Transactional
    public void updatePayment(Long id, PaymentUpdateRequest request) {
        Payment payment = getById(id);
        payment.updatePayment(request.name(), request.amount(), request.date(), PaymentStatus.getPaymentStatus(request.status()), PaymentType.getPaymentType(request.type()));
    }

    @Transactional
    public void deletePayment(Long id) {
        paymentRepository.delete(getById(id));
    }

    @Transactional
    public void deleteAllPayments() {
        paymentRepository.deleteAllInBatch();
    }

    // ===== 메소드 ===== //

    // 납부 -> 환불 -> 미납 우선순위 순
    public PaymentStatus getPaymentStatus(String studentId) {
        return paymentRepository.findAllByStudentId(studentId).stream()
                .map(Payment::getStatus)
                .reduce(PaymentStatus.NONE, (acc, status) -> {
                    if (status == PaymentStatus.PAID) return PaymentStatus.PAID;
                    if (status == PaymentStatus.REFUNDED) return PaymentStatus.REFUNDED;
                    return acc;
                });
    }

    // ===== 사업 참여 ===== //

    @Transactional
    public void addBusinessParticipation(Long paymentId, Long businessId) {
        Payment payment = getById(paymentId);
        Business business = businessService.getById(businessId);

        PaymentBusinessParticipation participation = PaymentBusinessParticipation.builder()
                .payment(payment)
                .business(business)
                .build();
        payment.addBusinessParticipation(participation);
        business.addBusinessParticipation(participation);
    }

    @Transactional
    public void removeBusinessParticipation(Long paymentId, Long businessId) {
        Payment payment = getById(paymentId);
        Business business = businessService.getById(businessId);

        payment.getParticipations()
                .stream()
                .filter(p -> p.getBusiness().getId().equals(businessId))
                .findFirst()
                .ifPresent(ep -> {
                    payment.removeBusinessParticipation(ep);
                    business.removeBusinessParticipation(ep);
                });
    }
}