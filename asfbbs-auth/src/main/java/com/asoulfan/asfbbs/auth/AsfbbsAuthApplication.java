package com.asoulfan.asfbbs.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import cn.hutool.jwt.JWTUtil;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class AsfbbsAuthApplication {

    public static void main(String[] args) {

        JWTUtil.createToken()
        SpringApplication.run(AsfbbsAuthApplication.class, args);
    }
}
