package com.studiop.dormmanagementsystem.entity;

import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import com.studiop.dormmanagementsystem.entity.enums.PaymentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "payment_id")
    private Long id;

    @Column(nullable = false)
    private String studentId;

    @Min(0)
    private Integer amount;

    @Column(nullable = false)
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;

    @Builder.Default
    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentBusinessParticipation> participations = new ArrayList<>();

    public void addBusinessParticipation(PaymentBusinessParticipation businessParticipation) {
        this.participations.add(businessParticipation);
    }

    public void removeBusinessParticipation(PaymentBusinessParticipation businessParticipation) {
        this.participations.remove(businessParticipation);
    }

    public void updatePayment(String depositName, int amount, LocalDate paymentDate, PaymentStatus paymentStatus, PaymentType paymentType) {
        this.studentId = depositName;
        this.amount = amount;
        this.date = paymentDate;
        this.status = paymentStatus;
        this.type = paymentType;
    }
}