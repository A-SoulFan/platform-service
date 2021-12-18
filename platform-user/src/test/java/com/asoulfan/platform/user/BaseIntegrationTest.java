package com.asoulfan.platform.user;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author asuka
 * @since 2021/08/24
 */
@SpringBootTest(classes = UserApplication.class)
public abstract class BaseIntegrationTest {
    static {
        System.setProperty("spring.profiles.active", "dev");
    }
}
