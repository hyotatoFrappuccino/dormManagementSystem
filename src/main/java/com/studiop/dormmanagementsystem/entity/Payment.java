package com.studiop.dormmanagementsystem.entity;

import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import com.studiop.dormmanagementsystem.entity.enums.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotBlank
    private String name;

    @NotNull
    @Range(min = 0)
    private Integer amount;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentType type;

    public Payment(Member member, String name, int amount, LocalDate date, PaymentStatus status, PaymentType type) {
        this.member = member;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.type = type;
    }

    public void updatePayment(String depositName, int amount, LocalDate paymentDate, PaymentStatus paymentStatus, PaymentType paymentType) {
        this.name = depositName;
        this.amount = amount;
        this.date = paymentDate;
        this.status = paymentStatus;
        this.type = paymentType;
    }
}
