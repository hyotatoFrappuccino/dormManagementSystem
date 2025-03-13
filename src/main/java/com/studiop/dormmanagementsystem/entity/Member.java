package com.studiop.dormmanagementsystem.entity;

import com.studiop.dormmanagementsystem.entity.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String studentId;

    private String name;

    private String phone;

    @ManyToOne
    @JoinColumn(name = "building_id")
    private Building building;

    private int roomNumber;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private boolean pledgeSubmitted;

    @OneToMany(mappedBy = "member")
    private List<Payment> payments = new ArrayList<>();

    public void setPaid() {
        this.paymentStatus = PaymentStatus.PAID;
    }

    //todo 생성자 고민
    public Member(String studentId, String name, PaymentStatus paymentStatus) {
        this.studentId = studentId;
        this.name = name;
        this.paymentStatus = paymentStatus;
    }
}
