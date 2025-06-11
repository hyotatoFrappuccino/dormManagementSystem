package com.studiop.dormmanagementsystem.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.OffsetDateTime;
import java.util.Map;

public record ErrorResponse(
        OffsetDateTime timestamp,
        int status,
        String error,
        String code,
        String message,
        String path,
        @JsonInclude(JsonInclude.Include.NON_EMPTY) Map<String, String> errors
) {
    public static ErrorResponse of(ErrorCode errorCode, String path) {
        return new ErrorResponse(
                OffsetDateTime.now(),
                errorCode.getHttpStatus().value(),
                errorCode.getHttpStatus().getReasonPhrase(),
                errorCode.name(),
                errorCode.getMessage(),
                path,
                null
        );
    }

    public static ErrorResponse of(ErrorCode errorCode, String path, Map<String, String> errors) {
        return new ErrorResponse(
                OffsetDateTime.now(),
                errorCode.getHttpStatus().value(),
                errorCode.getHttpStatus().getReasonPhrase(),
                errorCode.name(),
                errorCode.getMessage(),
                path,
                errors
        );
    }
}
