/*
 * 小芸 Inc.
 * Copyright (c) 2020-2021 All Rights Reserved.
 */
package com.asoulfan.asfbbs.user;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author asuka
 * @since 2021/07/24
 */
@SpringBootTest(classes = AsfbbsUserApplication.class)
public abstract class BaseTest {
    // static {
    //     System.setProperty(AsfbbsUserApplication.SPRING_PROFILES_ACTIVE, "dev");
    //     System.setProperty(Application.LOG_OUTPUT_TYPE, "console");
    // }
}
