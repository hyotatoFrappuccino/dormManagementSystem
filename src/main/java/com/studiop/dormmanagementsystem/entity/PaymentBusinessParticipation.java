package com.studiop.dormmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class PaymentBusinessParticipation extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "paymentBusinessParticipation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Business business;
}