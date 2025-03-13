package com.studiop.dormmanagementsystem.service;

import com.studiop.dormmanagementsystem.entity.AppConfig;
import com.studiop.dormmanagementsystem.repository.AppConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppConfigService {

    private final AppConfigRepository appConfigRepository;

    public String getConfigValue(String key, String defaultValue) {
        return appConfigRepository.findByConfigKey(key)
                .map(AppConfig::getConfigValue)
                .orElse(defaultValue);
    }

    public void setConfigValue(String key, String value) {
        AppConfig config = appConfigRepository.findByConfigKey(key)
                .orElse(new AppConfig(null, key, value, null));

        appConfigRepository.save(config);
    }

}
