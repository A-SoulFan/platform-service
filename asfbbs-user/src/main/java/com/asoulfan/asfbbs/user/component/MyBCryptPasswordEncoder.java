package com.asoulfan.asfbbs.user.component;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @program: ASFBBS
 * @description: security 加密
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-29
 **/
@Component
public class MyBCryptPasswordEncoder extends BCryptPasswordEncoder {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
