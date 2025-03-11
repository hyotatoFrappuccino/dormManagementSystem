package com.studiop.dormmanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotBlank
    private String name;

    @NotNull
    @Range(min = 0)
    private Integer amount;

    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    public Payment(Member member, String name, int amount, LocalDate paymentDate, PaymentStatus paymentStatus, PaymentType paymentType) {
        this.member = member;
        this.name = name;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
    }

    public void editPayment(String depositName, int amount, LocalDate paymentDate, PaymentStatus paymentStatus, PaymentType paymentType) {
        this.name = depositName;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
        this.paymentType = paymentType;
    }
}
