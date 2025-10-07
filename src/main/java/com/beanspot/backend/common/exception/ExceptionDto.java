package com.beanspot.backend.common.exception;

/** 클라이언트에 내려가는 에러 바디 */
public class ExceptionDto {
    private final Integer code;
    private final String message;

    public ExceptionDto(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
    public static ExceptionDto of(ErrorCode errorCode) { return new ExceptionDto(errorCode); }

    public Integer getCode() { return code; }
    public String getMessage() { return message; }
}

