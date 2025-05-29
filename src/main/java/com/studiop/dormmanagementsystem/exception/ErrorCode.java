package com.studiop.dormmanagementsystem.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // auth
    ILLEGAL_REGISTRATION_ID(NOT_ACCEPTABLE, "illegal registration id"),
    TOKEN_EXPIRED(UNAUTHORIZED, "토큰이 만료되었습니다."),
    INVALID_TOKEN(UNAUTHORIZED, "올바르지 않은 토큰입니다."),
    INVALID_JWT_SIGNATURE(UNAUTHORIZED, "잘못된 JWT 시그니처입니다."),
    FAILED_AUTHORIZED(UNAUTHORIZED, "인증에 실패하였습니다."),

    // admin
    ADMIN_NOT_FOUND(NOT_FOUND, "등록되지 않은 관리자입니다."),

    // global
    NO_ACCESS(FORBIDDEN, "접근 권한이 없습니다."),
    RESOURCE_NOT_FOUND(NOT_FOUND, "요청한 자원을 찾을 수 없습니다."),
    INVALID_REQUEST(BAD_REQUEST, "올바르지 않은 요청입니다."),
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "예상치못한 에러가 발생했습니다."),
    DATA_INTEGRITY_VIOLATION(BAD_REQUEST, "입력 데이터 오류(이미 존재하는 데이터와 중복되거나, 필수 입력값을 입력하지 않았을 수 있습니다.)");

    private final HttpStatus httpStatus;
    private final String message;
}
