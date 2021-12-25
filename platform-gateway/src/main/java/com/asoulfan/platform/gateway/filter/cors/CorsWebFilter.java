package com.asoulfan.platform.gateway.filter.cors;

import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.alibaba.fastjson.JSON;
import com.asoulfan.platform.common.nacos.NacosValue;
import com.asoulfan.platform.gateway.common.GatewayConstants;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author asuka
 * @date 2021/12/25
 */
@Slf4j
@Component
public class CorsWebFilter implements WebFilter, GlobalFilter, Ordered {
    @NacosValue(group = GatewayConstants.NACOS_GROUP_ID, dataId = "corsConfig")
    private CorsConfig corsConfig = new CorsConfig();

    public static final String ANY = "*";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        if (CorsUtils.isCorsRequest(request) && !checkIsValidCors(request)) {
            rejectRequest(response);
            return Mono.empty();
        }
        return chain.filter(exchange);
    }

    private boolean checkIsValidCors(ServerHttpRequest request) {
        String origin = request.getHeaders().getFirst(HttpHeaders.ORIGIN);
        boolean allowedOrigin = corsConfig.getAllowOrigin().contains(ANY) || corsConfig.getAllowOrigin().stream().anyMatch(allowOrigin -> StringUtils.equals(allowOrigin, origin));
        if (!allowedOrigin) {
            log.error("request url: {} use not allowed origin: {}", request.getURI(), origin);
            return false;
        }

        String method = request.getMethodValue();
        boolean allowedMethod = corsConfig.getAllowMethod().contains(ANY) || corsConfig.getAllowMethod().stream().anyMatch(allowMethod -> StringUtils.equals(allowMethod, method));
        if (!allowedMethod) {
            log.error("request url: {} use not allowed method: {}", request.getURI(), method);
            return false;
        }

        HttpHeaders requestHeaders = request.getHeaders();
        Set<String> headerKeys = requestHeaders.keySet();
        if (CollectionUtils.isEmpty(headerKeys) || corsConfig.getAllowHeader().contains(ANY)) {
            return true;
        }
        boolean allowedHeaders = headerKeys.stream().anyMatch(header -> corsConfig.getAllowHeader().contains(header));
        if (!allowedHeaders) {
            log.error("request url: {} use not allowed headers: {}", request.getURI(), JSON.toJSONString(headerKeys));
            return false;
        }
        return true;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            String origin = exchange.getRequest().getHeaders().getFirst(HttpHeaders.ORIGIN);
            if (origin != null) {
                ServerHttpResponse response = exchange.getResponse();
                HttpHeaders responseHeaders = response.getHeaders();
                responseHeaders.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN);
                responseHeaders.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS);
                responseHeaders.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS);
                responseHeaders.remove(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS);

                responseHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
                responseHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                responseHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, String.join(",", corsConfig.getAllowMethod()));
                responseHeaders.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, String.join(",", corsConfig.getAllowHeader()));
            }
        }));
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER + 1;
    }

    private void rejectRequest(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
    }
}
