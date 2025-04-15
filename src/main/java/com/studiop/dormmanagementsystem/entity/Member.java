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
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String studentId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "building_id")
    private Building building;

    @Column(nullable = false)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    private int warningCount;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FridgeApplication> fridgeApplications = new ArrayList<>();

    public void addFridgeApplication(FridgeApplication application) {
        this.fridgeApplications.add(application);
    }

    public void removeFridgeApplication(FridgeApplication application) {
        this.fridgeApplications.remove(application);
    }

    public void increaseWarningCount() {
        this.warningCount++;
    }

    public void decreaseWarningCount() {
        if (this.warningCount > 0) {
            this.warningCount--;
        }
    }

    public Member(String studentId, String name, String phone, Building building, String roomNumber, PaymentStatus paymentStatus) {
        this.studentId = studentId;
        this.name = name;
        this.phone = phone;
        this.building = building;
        this.roomNumber = roomNumber;
        this.paymentStatus = paymentStatus;
    }
}