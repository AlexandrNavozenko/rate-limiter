package com.mentor.rateLimiter.model;

import com.mentor.rateLimiter.util.SleepUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TokenBucketTest {

    private TokenBucket tokenBucket;

    @BeforeEach
    public void init() {
        tokenBucket = new TokenBucket(3);
    }

    @Test
    public void shouldReturnTrueWhenBucketIsNotFull() {
        for (int i = 0; i <= 3; i++) {
            assertTrue(tokenBucket.isConsumeToken());
            SleepUtil.sleep(TimeUnit.SECONDS, 20);
        }
    }

    @Test
    public void shouldReturnTrueWhenBucketIsFull() {
        for (int i = 0; i <= 2; i++) {
            assertTrue(tokenBucket.isConsumeToken());
            SleepUtil.sleep(TimeUnit.SECONDS, 5);
        }

        assertFalse(tokenBucket.isConsumeToken());
    }
}
