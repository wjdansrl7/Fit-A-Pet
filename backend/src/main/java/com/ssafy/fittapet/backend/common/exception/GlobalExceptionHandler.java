package com.ssafy.fittapet.backend.common.exception;

import com.ssafy.fittapet.backend.common.constant.error_code.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ErrorResponse> handleException(
            BindException e) {
        Map<String, String> errorMap = new HashMap<>();

        if (e.hasErrors()) {
            BindingResult bindingResult = e.getBindingResult();

            bindingResult.getFieldErrors().forEach(
                    fieldError ->
                            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        String errorMapString = errorMap.toString();

        return ResponseEntity
                .badRequest()
                .body(new ErrorResponse(
                        HttpStatus.BAD_REQUEST, errorMapString));
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleException(
            CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus httpStatus = errorCode.getHttpStatus();

        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus, errorCode.getMessage());

        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(
            Exception e) {
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(
                httpStatus, e.getMessage());

        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }
}


