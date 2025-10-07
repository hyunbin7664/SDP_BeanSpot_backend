package com.beanspot.backend.common.exception;

import org.springframework.http.HttpStatus;

/** 에러 코드(도메인별 범위는 나중에 확장) */
public enum ErrorCode {
    CALENDAR_SCHEDULE_NOT_FOUND(11001, HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다."),
    CALENDAR_INVALID_REPEAT_RULE(11002, HttpStatus.BAD_REQUEST, "유효하지 않은 반복 주기입니다."),
    INTERNAL_SERVER_ERROR(90000, HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(int code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public int getCode() { return code; }
    public HttpStatus getHttpStatus() { return httpStatus; }
    public String getMessage() { return message; }
}

