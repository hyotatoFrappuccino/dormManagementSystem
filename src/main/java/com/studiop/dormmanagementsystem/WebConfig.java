package com.studiop.dormmanagementsystem;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//todo CORS 에러 해결을 위한 임시 조치

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:3000")  // React 서버 주소만 허용
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 필요한 HTTP 메서드 추가
                        .allowedHeaders("*") // 모든 헤더 허용
                        .allowCredentials(true); // 쿠키 및 인증 정보 허용
            }
        };
    }
}
