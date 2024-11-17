package com.ssafy.fittapet.backend.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "refreshToken")
@Getter
@Builder
public class RefreshToken {

    @Id
    private String userUniqueName;
    private String token;

    @TimeToLive
    private Long expiration;
}