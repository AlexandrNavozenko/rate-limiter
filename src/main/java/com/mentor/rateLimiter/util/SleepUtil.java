package com.mentor.rateLimiter.util;

import java.util.concurrent.TimeUnit;

public class SleepUtil {

    private SleepUtil() {

    }

    public static void sleep(TimeUnit timeUnit, int timeout) {
        try {
            timeUnit.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
