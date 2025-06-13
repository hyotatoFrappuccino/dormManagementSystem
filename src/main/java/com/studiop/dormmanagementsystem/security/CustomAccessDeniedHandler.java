package com.studiop.dormmanagementsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

import static com.studiop.dormmanagementsystem.exception.ErrorCode.NO_ACCESS;
import static com.studiop.dormmanagementsystem.exception.GlobalExceptionHandler.*;

@Slf4j
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private static final String CHARACTER_ENCODING = "UTF-8";
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
//        log.error("AccessDeniedException is occurred. ", authException);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CHARACTER_ENCODING);
        response.setStatus(NO_ACCESS.getHttpStatus().value());
        objectMapper.writeValue(response.getWriter(), toResponse(NO_ACCESS, request.getRequestURI()));
    }
}