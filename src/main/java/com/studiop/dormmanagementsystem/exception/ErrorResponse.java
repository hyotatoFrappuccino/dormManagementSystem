package com.studiop.dormmanagementsystem.exception;

public record ErrorResponse(
        ErrorCode errorCode,
        String message
) {

}
