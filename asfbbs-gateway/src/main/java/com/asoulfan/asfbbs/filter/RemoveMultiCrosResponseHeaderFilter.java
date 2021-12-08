package com.asoulfan.asfbbs.filter;

import static org.springframework.cloud.gateway.support.GatewayToStringStyler.filterToStringCreator;


import java.util.List;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author asuka
 * @date 2021/12/08
 */
@Component
@Slf4j
public class RemoveMultiCrosResponseHeaderFilter extends AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig> {

    @Override
    public GatewayFilter apply(NameConfig config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange,
                    GatewayFilterChain chain) {
                //todo 网关应该只信任自己提供的跨域选项，后续讨论后优化掉
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    List<String> removed = exchange.getResponse().getHeaders().remove(config.getName());
                    if (!CollectionUtils.isEmpty(removed)) {
                        exchange.getResponse().getHeaders().add(config.getName(), removed.get(0));
                    }
                }));
            }

            @Override
            public String toString() {
                return filterToStringCreator(
                        RemoveMultiCrosResponseHeaderFilter.this)
                        .append("name", config.getName()).toString();
            }
        };
    }
}
