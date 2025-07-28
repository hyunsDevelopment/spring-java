package com.kiwoom.app.system.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final BusinessExceptionCode businessExceptionCode;

    public BusinessException(BusinessExceptionCode businessExceptionCode) {
        super(businessExceptionCode.getMessage());
        this.businessExceptionCode = businessExceptionCode;
    }
}
