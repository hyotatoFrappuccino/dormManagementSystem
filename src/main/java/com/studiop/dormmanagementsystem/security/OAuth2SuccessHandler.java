package com.studiop.dormmanagementsystem.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Value("${url.frontend}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // accessToken, refreshToken 발급
        String accessToken = tokenProvider.generateAccessToken(authentication);
        tokenProvider.generateRefreshToken(authentication, accessToken);
        
        // 토큰 전달을 위한 redirect
        String redirectUrl = UriComponentsBuilder.fromUriString(frontendUrl + "/oauth/success")
                .queryParam("accessToken", accessToken)
                .build().toUriString();

        log.info("authentication.getPrincipal(): {}", authentication.getPrincipal());
        log.info("accessToken: {}", accessToken);

        response.sendRedirect(redirectUrl);
    }
}