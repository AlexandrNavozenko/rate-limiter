package com.mentor.rateLimiter.model;

import java.util.concurrent.atomic.AtomicInteger;

public class TokenBucket {

    private int capacity;

    private AtomicInteger tokenCount;

    private Long lastRefillTime;

    private int accessTokenMinutes;

    public TokenBucket(final int capacity) {
        this.capacity = capacity;
        this.tokenCount = new AtomicInteger(capacity);
        this.lastRefillTime = System.currentTimeMillis();
        this.accessTokenMinutes = 1;
    }

    public TokenBucket(final int capacity, final int accessTokenMinutes) {
        this.capacity = capacity;
        this.tokenCount = new AtomicInteger(capacity);
        this.lastRefillTime = System.currentTimeMillis();
        this.accessTokenMinutes = accessTokenMinutes;
    }

    public synchronized boolean isConsumeToken() {
        refillTokenCount();
        if (tokenCount.get() > 0) {
            tokenCount.decrementAndGet();
            return true;
        }

        return false;
    }

    private void refillTokenCount() {
        var currentTime = System.currentTimeMillis();
        var elapsedTime = currentTime - lastRefillTime;
        int elapsedMinutes = (int) (elapsedTime / (accessTokenMinutes * 60 * 1000));
        if (elapsedMinutes > 0) {
            tokenCount.set(capacity);
            lastRefillTime = currentTime;
        }
    }
}
