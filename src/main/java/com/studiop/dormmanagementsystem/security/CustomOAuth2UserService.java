package com.studiop.dormmanagementsystem.security;

import com.studiop.dormmanagementsystem.entity.Admin;
import com.studiop.dormmanagementsystem.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final AdminRepository adminRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // 1. 유저 정보(attributes) 가져오기 [sub, name, email ...]
        Map<String, Object> oAuth2UserAttributes = super.loadUser(userRequest).getAttributes();
        log.info("oAuth2UserAttributes: {}", oAuth2UserAttributes);
        
        // 2. registrationId 가져오기 (third-party id. yml에서 설정한 client.registration의 값[google])
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        
        // 3. userNameAttributeName 가져오기
        // oauth 관련 yml에서 설정한 provider의 user-name-attribute 값. (구글은 "sub". CommonOAuth2Provider에서 확인 가능)
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        
        // 4. 유저 정보 dto 생성
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, oAuth2UserAttributes);
        
        // 5. 등록된 관리자 가져오기 (회원가입 불가)
        Admin admin = adminRepository.findByEmail(oAuth2UserInfo.email())
                .orElseThrow(() -> new OAuth2AuthenticationException(new OAuth2Error("admin_not_found", "등록된 관리자가 아닙니다.", null)));
        
        // 6. OAuth2User로 반환
        return new PrincipalDetails(admin, oAuth2UserAttributes, userNameAttributeName);
    }
}