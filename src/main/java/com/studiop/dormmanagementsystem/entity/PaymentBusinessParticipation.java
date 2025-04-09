package com.studiop.dormmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
    public class PaymentBusinessParticipation {

    @Id
    @GeneratedValue
    @Column(name = "paymentBusinessParticipation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Business business;

    public PaymentBusinessParticipation(Payment payment, Business business) {
        this.payment = payment;
        this.business = business;
    }
}
