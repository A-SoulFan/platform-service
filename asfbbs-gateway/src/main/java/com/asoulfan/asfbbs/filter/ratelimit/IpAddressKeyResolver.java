package com.asoulfan.asfbbs.filter.ratelimit;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @program: ASFBBS
 * @description: 限流过滤器使用，根据ip限流
 * @packagename: com.asoulfan.asfbbs.filter
 * @author: anti
 * @date: 2021-08-27 22:10
 **/
@Component
public class IpAddressKeyResolver implements KeyResolver {
    private static final String DEFAULT_IP="127.0.0.1";

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(getIpAddress(exchange));
    }

    private String getIpAddress(ServerWebExchange exchange){
        String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        return ip == null?DEFAULT_IP:ip;
    }



}