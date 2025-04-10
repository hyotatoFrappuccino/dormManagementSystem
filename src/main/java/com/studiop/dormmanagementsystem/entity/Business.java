package com.studiop.dormmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Business extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "business_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentBusinessParticipation> participations = new ArrayList<>();

    public void addBusinessParticipation(PaymentBusinessParticipation businessParticipation) {
        this.participations.add(businessParticipation);
    }

    public void removeBusinessParticipation(PaymentBusinessParticipation businessParticipation) {
        this.participations.remove(businessParticipation);
    }

    public Business(String name) {
        this.name = name;
    }
}
