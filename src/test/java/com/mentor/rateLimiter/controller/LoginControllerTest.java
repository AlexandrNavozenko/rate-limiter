package com.mentor.rateLimiter.controller;

import com.mentor.rateLimiter.exception.LoginRateLimitException;
import com.mentor.rateLimiter.service.UserTokenBucketService;
import com.mentor.rateLimiter.util.SleepUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.concurrent.TimeUnit;

import static com.mentor.rateLimiter.controller.LoginController.LOGIN_RATE_LIMIT_EXCEEDED_FOR_USER;
import static com.mentor.rateLimiter.controller.LoginController.LOGIN_SUCCESSFUL_FOR_USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginControllerTest {

    private LoginController loginController = new LoginController(new UserTokenBucketService());

    @Test
    public void shouldReturnSuccessMessageAfterFortySeconds() {
        final String user1 = "Alexandr";
        final String user2 = "Alex";

        for (int i = 0; i < 4; i++) {
            assertThat(LOGIN_SUCCESSFUL_FOR_USER + user1).isEqualTo(loginController.login(user1));
            assertThat(LOGIN_SUCCESSFUL_FOR_USER + user2).isEqualTo(loginController.login(user2));
            SleepUtil.sleep(TimeUnit.SECONDS, 40);
        }
    }

    @Test
    public void shouldReturnSuccessMessageAfterFiftySeconds() {
        final String user1 = "Alexandr";
        final String user2 = "Alex";

        for (int i = 0; i <= 2; i++) {
            assertThat(LOGIN_SUCCESSFUL_FOR_USER + user1).isEqualTo(loginController.login(user1));
            assertThat(LOGIN_SUCCESSFUL_FOR_USER + user2).isEqualTo(loginController.login(user2));
            SleepUtil.sleep(TimeUnit.SECONDS, 5);
        }

        String parsErrMsg1 = String.format(LOGIN_RATE_LIMIT_EXCEEDED_FOR_USER, user1);
        var exception = assertThrows(LoginRateLimitException.class, () -> loginController.login(user1));
        assertEquals(parsErrMsg1, exception.getMessage());

        String parsErrMsg2 = String.format(LOGIN_RATE_LIMIT_EXCEEDED_FOR_USER, user2);
        exception = assertThrows(LoginRateLimitException.class, () -> loginController.login(user2));
        assertEquals(parsErrMsg2, exception.getMessage());
    }
}
