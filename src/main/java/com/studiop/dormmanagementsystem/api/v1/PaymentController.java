package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.entity.Payment;
import com.studiop.dormmanagementsystem.service.PaymentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    /**납부자 추가*/
    @PostMapping
    public ResponseEntity<String> addPayment(@RequestBody Payment payment) {
        paymentService.addPayment(
                payment.getName(),
                payment.getAmount(),
                payment.getDate(),
                payment.getType());
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
    @PutMapping()
    public ResponseEntity<String> paymentEdit(@RequestBody Payment payment) {
        try {
            paymentService.editPayment(
                    payment.getId(),
                    payment.getName(),
                    payment.getAmount(),
                    payment.getDate(),
                    payment.getStatus(),
                    payment.getType()
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
