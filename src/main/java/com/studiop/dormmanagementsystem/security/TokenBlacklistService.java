package com.studiop.dormmanagementsystem.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    private final TokenProvider tokenProvider;

    private static final String BLACKLIST_PREFIX = "blacklist:token:";
    // 토큰을 블랙리스트로 추가
    public void addTokenToBlacklist(String accessToken) {
        if (!StringUtils.hasText(accessToken)) {
            return;
        }

        try {
            // 토큰의 남은 만료시간 계산
            long expiration = tokenProvider.getTokenExpiration(accessToken);
            long currentTime = System.currentTimeMillis();
            long remainingTime = expiration - currentTime;

            if (remainingTime > 0) {
                String key = BLACKLIST_PREFIX + accessToken;
                redisTemplate.opsForValue().set(key, "blacklisted", remainingTime, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            log.error("Failed to add token to blacklist: {}", e.getMessage());
        }
    }

    // 토큰이 블랙리스트인지 확인
    public boolean isTokenBlacklisted(String accessToken) {
        if (!StringUtils.hasText(accessToken)) {
            return false;
        }

        try {
            String key = BLACKLIST_PREFIX + accessToken;
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e) {
            log.error("Failed to check token blacklist status: {}", e.getMessage());
            return false;
        }
    }
}
