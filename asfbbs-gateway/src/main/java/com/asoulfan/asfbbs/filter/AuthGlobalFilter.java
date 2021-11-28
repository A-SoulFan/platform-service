package com.asoulfan.asfbbs.filter;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.asoulfan.common.constant.AuthConstant;
import com.nimbusds.jose.JWSObject;

import cn.hutool.core.util.URLUtil;
import reactor.core.publisher.Mono;

/**
 * 将登录用户的JWT转为用户信息的全局过滤器
 *
 * @author Cscar
 * @since 2021-07-28 16:39
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    private static Logger LOGGER = LoggerFactory.getLogger(AuthGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //优先取cookie里的token，允许被替换
        String token = null;
        HttpCookie cookie = exchange.getRequest().getCookies().getFirst(AuthConstant.USER_TOKEN_COOKIE_KEY);
        if (cookie != null) {
            token = cookie.getValue();
        }
        if (token == null) {
            token = exchange.getRequest().getHeaders().getFirst(AuthConstant.JWT_TOKEN_HEADER);
        }

        if (token == null) {
            return chain.filter(exchange);
        }

        try {
            //从token中解析用户信息并设置到Header中去
            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
            JWSObject jwsObject = JWSObject.parse(realToken);
            //解决中文乱码问题
            String userStr = URLUtil.encode(jwsObject.getPayload().toString());
            LOGGER.info("AuthGlobalFilter.filter() user:{}", userStr);
            ServerHttpRequest request = exchange.getRequest().mutate().header(AuthConstant.USER_TOKEN_HEADER, userStr).build();
            exchange = exchange.mutate().request(request).build();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}