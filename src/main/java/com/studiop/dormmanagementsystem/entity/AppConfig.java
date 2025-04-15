package com.studiop.dormmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "app_config")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AppConfig extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String configKey;

    @Column(nullable = false)
    private String configValue;
}