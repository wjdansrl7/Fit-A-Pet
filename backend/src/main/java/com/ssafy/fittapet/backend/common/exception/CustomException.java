package com.ssafy.fittapet.backend.common.exception;

import com.ssafy.fittapet.backend.common.constant.error_code.ErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends Exception {

    private final ErrorCode errorCode;

    public CustomException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
