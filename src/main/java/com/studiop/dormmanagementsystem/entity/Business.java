package com.studiop.dormmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Business extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "business_id")
    private Long id;

    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentBusinessParticipation> participations = new ArrayList<>();

    public void addBusinessParticipation(PaymentBusinessParticipation businessParticipation) {
        this.participations.add(businessParticipation);
    }

    public void removeBusinessParticipation(PaymentBusinessParticipation businessParticipation) {
        this.participations.remove(businessParticipation);
    }
}
