package com.studiop.dormmanagementsystem.exception;

public class EntityException extends CustomException {

    public EntityException(ErrorCode errorCode) {
        super(errorCode);
    }
}
