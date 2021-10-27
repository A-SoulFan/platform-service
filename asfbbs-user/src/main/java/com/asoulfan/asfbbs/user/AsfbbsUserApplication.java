package com.asoulfan.asfbbs.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-29
 **/
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan("com.asoulfan.asfbbs.user.mapper")
@EnableEncryptableProperties
@SpringBootApplication(scanBasePackages = "com.asoulfan.asfbbs")
public class AsfbbsUserApplication {
    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(AsfbbsUserApplication.class, args);
    }
}
