package com.studiop.dormmanagementsystem.api.v1;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @GetMapping("/user")
    public ResponseEntity<Map<String, String>> getUser(@AuthenticationPrincipal OAuth2User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Unauthorized"));
        }

        String email = principal.getAttribute("email");
        String sub = principal.getAttribute("sub");
        if (email == null || sub == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "BAD REQUEST"));
        }

        return ResponseEntity.ok(Map.of("email", email, "sub", sub));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        // Spring Security 컨텍스트 초기화 (사용자 인증 정보 제거)
        SecurityContextHolder.clearContext();

        // 현재 세션 무효화
        request.getSession().invalidate();

        // 로그아웃 성공 응답 반환
        return ResponseEntity.ok().build();
    }

}
