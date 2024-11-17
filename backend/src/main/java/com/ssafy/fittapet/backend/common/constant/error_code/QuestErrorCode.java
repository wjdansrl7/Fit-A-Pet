package com.ssafy.fittapet.backend.common.constant.error_code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum QuestErrorCode implements ErrorCode {
    NO_QUEST(HttpStatus.NOT_FOUND, "해당 퀘스트가 없습니다."),
    NOT_AVAILABLE_CATEGORY(HttpStatus.NOT_FOUND, "존재하지 않는 카테고리입니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
