package com.asoulfan.asfbbs.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import com.asoulfan.asfbbs.factory.ResponseModifyGatewayFilterFactory;


@Component
@Configuration
public class CustomizeFilterConfig {
    @Bean
    /***
     * 初始化返回数据修改过滤器
     */
    public ResponseModifyGatewayFilterFactory responseBodyModifyGatewayFilterFactory() {
        return new ResponseModifyGatewayFilterFactory(HandlerStrategies.withDefaults().messageReaders(),Collections.emptySet(),Collections.emptySet());
    }
}
