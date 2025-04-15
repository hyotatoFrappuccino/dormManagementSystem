package com.studiop.dormmanagementsystem.repository;

import com.studiop.dormmanagementsystem.entity.AppConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppConfigRepository extends JpaRepository<AppConfig, Long> {
    Optional<AppConfig> findByConfigKey(String key);
}