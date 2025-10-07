package com.beanspot.backend.common.exception;

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) { this.errorCode = errorCode; }

    public ErrorCode getErrorCode() { return errorCode; }

    @Override public String getMessage() { return errorCode.getMessage(); }
}
