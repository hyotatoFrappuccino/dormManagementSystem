package com.studiop.dormmanagementsystem.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

    @Value("${url.frontend}")
    private String frontendUrl;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
//        log.error("OAuth2 login fail. ", exception);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(String.format("""
                {
                  "code": "FAILED_LOGIN",
                  "message": "소셜 로그인에 실패하였습니다. %s"
                }
                """, exception.getMessage()));
        if (((OAuth2AuthenticationException) exception).getError().getErrorCode().equals("admin_not_found")) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.sendRedirect(frontendUrl + "/unauthorized");
        }
    }
}
