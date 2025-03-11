package com.studiop.dormmanagementsystem.controller;

import com.studiop.dormmanagementsystem.entity.Payment;
import com.studiop.dormmanagementsystem.entity.PaymentType;
import com.studiop.dormmanagementsystem.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * 납부자 추가
     *
     * @param depositName 학번(입금자명)
     * @param amount      금액
     * @param depositDate 입금일
     * @param paymentType 납부유형(BANK_TRANSFER, ON_SITE)
     * @return HTTP 200 OK
     */
    @PostMapping
    public ResponseEntity<String> addPayment(@RequestParam String depositName,
                                             @RequestParam int amount,
                                             @RequestParam LocalDate depositDate,
                                             @RequestParam PaymentType paymentType) {
        paymentService.addPayment(depositName, amount, depositDate, paymentType);
        return ResponseEntity.ok("납부자 추가 완료");
    }

    /**
     * 납부자 목록 반환
     **/
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayers() {
        List<Payment> allPayers = paymentService.getAllPayers();
        return ResponseEntity.ok(allPayers);
    }

    /**
     * 납부자 수정
     **/
    @PutMapping("/{paymentId}")
    public ResponseEntity<String> paymentEdit(@PathVariable Long paymentId, @RequestBody Payment payment) {
        try {
            paymentService.editPayment(
                    paymentId,
                    payment.getName(),
                    payment.getAmount(),
                    payment.getPaymentDate(),
                    payment.getPaymentStatus(),
                    payment.getPaymentType()
            );
            return ResponseEntity.ok("납부자 정보 수정 완료");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("납부자 업데이트 오류");
        }
    }

    /** 납부자 정보 삭제 **/
    @DeleteMapping("/{paymentId}")
    public ResponseEntity<String> deletePayment(@PathVariable Long paymentId) {
        try {
            paymentService.deletePayment(paymentId);
            return ResponseEntity.ok("납부자 삭제 완료");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("납부자 삭제 오류");
        }
    }

}
