package com.kiwoom.app.system.exception;

import lombok.Getter;

@Getter
public enum BusinessExceptionCode {

    // 예제
    UNAUTHORIZED(1000, "로그인이 필요합니다."),
    FORBIDDEN(1001, "접근 권한이 없습니다."),
    USER_NOT_FOUND(2100, "ID 또는 비밀번호를 확인해주세요."),

    // TODO 오류 케이스 정의 필요
    ;

    private final int code;
    private final String message;

    BusinessExceptionCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
