package com.ssafy.fittapet.backend.common.constant.error_code;


import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getHttpStatus();

    String getMessage();
}
