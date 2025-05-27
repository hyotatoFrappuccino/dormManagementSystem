package com.studiop.dormmanagementsystem.exception;

public class GoogleSheetException extends CustomException {

    public GoogleSheetException(ErrorCode errorCode) {
        super(errorCode);
    }

    public GoogleSheetException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}