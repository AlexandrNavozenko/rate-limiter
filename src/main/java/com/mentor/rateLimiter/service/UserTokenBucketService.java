package com.mentor.rateLimiter.service;

import com.mentor.rateLimiter.model.TokenBucket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenBucketService {

    public static final int DEFAULT_CAPACITY = 3;

    private ConcurrentHashMap<String, TokenBucket> tokenBuckets = new ConcurrentHashMap<>();

    public boolean allowLogin(final String userName) {
        TokenBucket tokenBucket = tokenBuckets.computeIfAbsent(userName, key ->new TokenBucket(DEFAULT_CAPACITY));

        return tokenBucket.isConsumeToken();
    }
}
