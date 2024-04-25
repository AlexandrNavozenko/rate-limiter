package com.mentor.rateLimiter.controller;

import com.mentor.rateLimiter.exception.LoginRateLimitException;
import com.mentor.rateLimiter.service.UserTokenBucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class LoginController {

    public static final String LOGIN_RATE_LIMIT_EXCEEDED_FOR_USER =
            "Login rate limit exceeded for user: %s. Please try again later.";

    public static final String LOGIN_SUCCESSFUL_FOR_USER = "Login successful for user: ";

    private final UserTokenBucketService userTokenBucketService;

    @PostMapping("/login")
    public String login(@RequestBody String userName) {
        if (userTokenBucketService.allowLogin(userName)) {
            return LOGIN_SUCCESSFUL_FOR_USER + userName;
        }

        throw new LoginRateLimitException(String
                .format(LOGIN_RATE_LIMIT_EXCEEDED_FOR_USER, userName));
    }
}
