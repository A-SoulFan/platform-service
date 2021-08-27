package com.asoulfan.asfbbs.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.asoulfan.asfbbs")
public class AsfbbsUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(AsfbbsUserApplication.class, args);
    }
}
