package com.asoulfan.asfbbs.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.asoulfan.asfbbs")
public class AsfbbsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsfbbsAdminApplication.class, args);
    }
}
