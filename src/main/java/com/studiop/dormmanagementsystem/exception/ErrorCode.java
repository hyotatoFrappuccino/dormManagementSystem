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

    // fridge
    APPLICATION_CONDITION_NOT_MET(BAD_REQUEST, "신청 요건이 충족되지 않았습니다. 납부 상태를 확인해주세요."),

    // round
    DUPLICATE_ROUND(BAD_REQUEST, "해당 기간과 겹치는 회차가 존재합니다."),

    // admin
    ADMIN_NOT_FOUND(NOT_FOUND, "등록되지 않은 관리자입니다."),
    INVALID_EMAIL_DOMAIN(BAD_REQUEST, "@gmail.com 이메일만 등록 가능합니다."),

    // google
    GOOGLE_SHEET_ID_NOT_FOUND(BAD_REQUEST, "설정에서 서약서 구글 시트 ID를 설정해주세요."),
    GOOGLE_SHEET_ID_INVALID(BAD_REQUEST, "설정 - 서약서 구글 시트 ID에 해당하는 구글 시트를 찾을 수 없음"),
    GOOGLE_EXCEED_API_REQUEST(TOO_MANY_REQUESTS, "API 요청 한도 초과. 잠시 후 다시 시도해주세요."),
    GOOGLE_NO_ACCESS(BAD_REQUEST, "설정 - 서약서 구글 시트 ID에 접근할 수 있는 권한이 없음. (도움말 - 서약서 구글 시트 설정 참조)"),
    GOOGLE_INVALID_API_KEY(BAD_REQUEST, "API 키 또는 OAuth 토큰이 유효하지 않음. 시스템 개발자에게 문의하세요."),
    GOOGLE_500(INTERNAL_SERVER_ERROR, "Google 서버에서 예기치 않은 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    GOOGLE_503(INTERNAL_SERVER_ERROR, "구글 서버측 문제로 인하여 일시적으로 데이터를 가져올 수 없음. 잠시 후 다시 시도해주세요. 문제가 지속된다면 https://status.cloud.google.com/ 사이트를 확인해주세요."),
    GOOGLE_ERROR(INTERNAL_SERVER_ERROR, "Google Sheets API 호출 중 알 수 없는 오류가 발생했습니다."),

    // global
    NO_ACCESS(FORBIDDEN, "접근 권한이 없습니다."),
    RESOURCE_NOT_FOUND(NOT_FOUND, "요청한 자원을 찾을 수 없습니다. 이미 다른 사용자가 수정하였거나 삭제하였을 수 있습니다."),
    INVALID_REQUEST(BAD_REQUEST, "올바르지 않은 요청입니다."),
    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, "예상치못한 에러가 발생했습니다."),
    DATA_INTEGRITY_VIOLATION(BAD_REQUEST, "입력 데이터 오류(이미 존재하는 데이터와 중복되거나, 필수 입력값을 입력하지 않았을 수 있습니다.)"),
    METHOD_FORBIDDEN(METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
