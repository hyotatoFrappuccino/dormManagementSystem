package com.studiop.dormmanagementsystem.exception;

public class EntityException extends CustomException {

    public EntityException(ErrorCode errorCode) {
        super(errorCode);
    }

    public EntityException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
