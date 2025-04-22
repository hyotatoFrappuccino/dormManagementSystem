package com.studiop.dormmanagementsystem.entity;

import com.studiop.dormmanagementsystem.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "admin_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public void changeName(String name) {
        this.name = name;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeRole(Role role) {
        this.role = role;
    }
}