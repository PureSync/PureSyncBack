package com.fcc.PureSync.context.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class TokenDao {
    private final StringRedisTemplate stringRedisTemplate;


    public void saveRefreshToken(String accessTokenUUID, String refreshToken, Long refreshTokenValidityInMilliseconds) {
        stringRedisTemplate.opsForValue().set(accessTokenUUID,refreshToken, Duration.ofMillis(refreshTokenValidityInMilliseconds));
    }
}
