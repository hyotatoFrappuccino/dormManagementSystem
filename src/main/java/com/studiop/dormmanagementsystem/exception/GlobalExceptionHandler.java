package com.studiop.dormmanagementsystem.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

import static com.studiop.dormmanagementsystem.exception.ErrorCode.*;

/**
 * 전역 예외 처리
 * ResponseEntityExceptionHandler를 상속
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 커스텀 예외 (CustomException)
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e, HttpServletRequest request) {
        return toResponse(e.getErrorCode(), request.getRequestURI());
    }

    // 접근 권한 없음 (AccessDeniedException)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        return toResponse(NO_ACCESS, request.getRequestURI());
    }

    // 데이터 정합성 위반 (DataIntegrityViolationException)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e,
                                                                        HttpServletRequest request) {
        return toResponse(DATA_INTEGRITY_VIOLATION, request.getRequestURI());
    }

    // 찾을 수 없는 리소스 (NoResourceFoundException)
    @Override
    @Nullable
    protected ResponseEntity<Object> handleNoResourceFoundException(
            NoResourceFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        return toResponse(RESOURCE_NOT_FOUND, extractPath(request));
    }

    // 지원하지 않는 HTTP 메서드 (HttpRequestMethodNotSupportedException)
    @Override
    @Nullable
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        return toResponse(METHOD_FORBIDDEN, extractPath(request));
    }

    // 메서드 인자 유효성 검사 실패 (MethodArgumentNotValidException)
    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request
    ) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            } else {
                String objectName = error.getObjectName();
                String errorMessage = error.getDefaultMessage();
                errors.put(objectName, errorMessage);
            }
        });

        return toResponseWithError(INVALID_REQUEST, extractPath(request), errors);
    }

    // 그 외의 예외 처리 (Spring MVC 내부 예외 처리)
    @Override
    @Nullable
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request
    ) {
        logger.error("Unexpected error 발생: " + ex.getMessage(), ex);
        return toResponse(INTERNAL_ERROR, extractPath(request));
    }

    // 그 외의 예외 처리 (컨트롤러 진입 후 예외 처리0
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedException(Exception ex, HttpServletRequest request) {
        logger.error("Unexpected error 발생: " + ex.getMessage(), ex);
        return toResponse(INTERNAL_ERROR, request.getRequestURI());
    }

    public static ResponseEntity<Object> toResponse(ErrorCode errorCode, String path) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode, path));
    }

    private static ResponseEntity<Object> toResponseWithError(ErrorCode errorCode, String path, Map<String, String> errors) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponse.of(errorCode, path, errors));
    }

    private String extractPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }
}