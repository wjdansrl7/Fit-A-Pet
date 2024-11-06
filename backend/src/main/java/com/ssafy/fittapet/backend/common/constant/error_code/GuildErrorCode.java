package com.ssafy.fittapet.backend.common.constant.error_code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum GuildErrorCode implements ErrorCode {
    NO_GUILD(HttpStatus.NOT_FOUND, "해당 길드가 없습니다."),
    NOT_GUILD_LEADER(HttpStatus.FORBIDDEN, "길드 리더가 아닙니다."),
    NOT_GUILD_MEMBER(HttpStatus.BAD_REQUEST, "길드 멤버가 아닙니다."),
    ALREADY_SET_QUEST(HttpStatus.CONFLICT, "이미 진행 중인 퀘스트입니다."),
    DUPLICATED_NAME(HttpStatus.NOT_ACCEPTABLE, "이미 존재하는 길듬명입니다."),

    NOT_IN_TIME(HttpStatus.BAD_REQUEST, "유효 기간이 지난 입장 코드입니다."),
    ALREADY_JOIN(HttpStatus.BAD_REQUEST, "이미 가입된 길드입니다."),
    FULL_GUILD(HttpStatus.NOT_ACCEPTABLE, "길드원이 가득 찼습니다."),
    NOT_AVAILABLE_POSITION(HttpStatus.BAD_REQUEST, "맵의 위치가 유효하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
