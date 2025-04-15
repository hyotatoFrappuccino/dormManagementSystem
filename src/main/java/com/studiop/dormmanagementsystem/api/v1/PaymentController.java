package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Payment;
import com.studiop.dormmanagementsystem.entity.dto.BusinessDto;
import com.studiop.dormmanagementsystem.entity.dto.PaymentDto;
import com.studiop.dormmanagementsystem.entity.dto.PaymentRequest;
import com.studiop.dormmanagementsystem.entity.dto.PaymentUpdateRequest;
import com.studiop.dormmanagementsystem.service.BusinessService;
import com.studiop.dormmanagementsystem.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final BusinessService businessService;

    @Operation(summary = "납부자 목록 조회")
    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<PaymentDto> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "납부자 조회")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable Long id) {
        PaymentDto payment = paymentService.findDtoById(id);
        return ResponseEntity.ok(payment);
    }

    @Operation(summary = "납부자 추가")
    @PostMapping
    public ResponseEntity<PaymentDto> addPayment(@RequestBody PaymentRequest request) {
        Payment savedPayment = paymentService.addPayment(request);
        URI location = URI.create("/api/v1/payments/" + savedPayment.getId());
        return ResponseEntity.created(location).body(PaymentDto.fromEntity(savedPayment));
    }

    @Operation(summary = "납부자 수정")
    @PutMapping("/{id}")
    public ResponseEntity<Void> editPayment(@PathVariable Long id, @RequestBody @Valid PaymentUpdateRequest request) {
        paymentService.updatePayment(id, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "납부자 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "전체 납부자 삭제")
    @DeleteMapping
    public ResponseEntity<Void> deleteAllPayments() {
        paymentService.deleteAllPayments();
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "사업 전체 조회")
    @GetMapping("/business")
    public ResponseEntity<List<BusinessDto>> getAllBusinesses() {
        return ResponseEntity.ok(businessService.getAllBusiness().stream().map(BusinessDto::fromEntity).toList());
    }

    @Operation(summary = "사업 참여 추가")
    @PostMapping("/{id}/business")
    public ResponseEntity<Void> addBusiness(@PathVariable Long id, @RequestBody Long businessId) {
        paymentService.addBusinessParticipation(id, businessId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "사업 참여 삭제")
    @DeleteMapping("/{id}/business")
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id, @RequestBody Long businessId) {
        paymentService.removeBusinessParticipation(id, businessId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "사업 추가")
    @PostMapping("/business")
    public ResponseEntity<String> addBusinessParticipation(@RequestBody String name) {
        businessService.addBusiness(name);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "사업 삭제")
    @DeleteMapping("/business/{id}")
    public ResponseEntity<String> deleteBusinessParticipation(@PathVariable Long id) {
        businessService.removeBusiness(id);
        return ResponseEntity.noContent().build();
    }
}
