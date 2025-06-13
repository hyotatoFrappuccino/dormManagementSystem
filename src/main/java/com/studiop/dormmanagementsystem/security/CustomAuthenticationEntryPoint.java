package com.studiop.dormmanagementsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

import static com.studiop.dormmanagementsystem.exception.ErrorCode.FAILED_AUTHORIZED;
import static com.studiop.dormmanagementsystem.exception.GlobalExceptionHandler.toResponse;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String CHARACTER_ENCODING = "UTF-8";
    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
//        log.error("AuthenticationException is occurred. ", authException);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
        response.setStatus(FAILED_AUTHORIZED.getHttpStatus().value());
        objectMapper.writeValue(response.getWriter(), toResponse(FAILED_AUTHORIZED, request.getRequestURI()));
    }
}
