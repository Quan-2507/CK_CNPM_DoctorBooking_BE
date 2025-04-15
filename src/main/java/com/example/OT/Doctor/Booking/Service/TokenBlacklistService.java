package com.example.OT.Doctor.Booking.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenBlacklistService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void blacklistToken(String token, long expiryTimeMs) {
        redisTemplate.opsForValue().set(token, "blacklisted", expiryTimeMs, TimeUnit.MILLISECONDS);
    }

    public boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }
}