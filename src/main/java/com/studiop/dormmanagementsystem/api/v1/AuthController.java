package com.studiop.dormmanagementsystem.api.v1;

import com.studiop.dormmanagementsystem.security.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenService tokenService;
    private final TokenBlacklistService tokenBlacklistService;

    @Operation(summary = "현재 로그인된 사용자 정보 조회")
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

    @GetMapping("/success")
    public ResponseEntity<LoginResponse> loginSuccess(@Valid LoginResponse loginResponse) {
        return ResponseEntity.ok(loginResponse);
    }

    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetails userDetails,
                                       @RequestHeader(value = AUTHORIZATION, required = false) String authHeader) {
        tokenService.deleteRefreshToken(userDetails.getUsername());

        String accessToken = resolveToken(authHeader);
        tokenBlacklistService.addTokenToBlacklist(accessToken);

        // 로그아웃 성공 응답 반환
        return ResponseEntity.ok().build();
    }

    private String resolveToken(String authHeader) {
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(TokenKey.TOKEN_PREFIX)) {
            return authHeader.substring(TokenKey.TOKEN_PREFIX.length());
        }
        return null;
    }
}