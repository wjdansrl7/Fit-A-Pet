package com.ssafy.fittapet.backend.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash(value = "blacklist")
@Getter
@Builder
public class Blacklist {

    @Id
    private String refreshToken;

    @TimeToLive
    private Long expiration;
}
