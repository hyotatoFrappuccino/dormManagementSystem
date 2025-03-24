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

    private String roomNumber;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "member")
    private List<Payment> payments = new ArrayList<>();

    @OneToMany
    private List<FridgeApplication> fridgeApplications = new ArrayList<>();

    public void setPaid() {
        this.paymentStatus = PaymentStatus.PAID;
    }

    public void addFridgeApplication(FridgeApplication application) {
        this.fridgeApplications.add(application);
    }

    //todo 생성자 고민
    public Member(String studentId, String name, String phone, Building building, String roomNumber, PaymentStatus paymentStatus) {
        this.studentId = studentId;
        this.name = name;
        this.phone = phone;
        this.building = building;
        this.roomNumber = roomNumber;
        this.paymentStatus = paymentStatus;
    }
}
