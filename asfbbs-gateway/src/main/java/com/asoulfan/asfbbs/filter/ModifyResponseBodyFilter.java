package com.asoulfan.asfbbs.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.asoulfan.common.api.CommonResult;
import com.asoulfan.common.api.ResultCode;
import com.asoulfan.common.api.SuccessWithExtraInfoResult;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * @author sensen
 * @since 2021-09-30
 */
@Component
@Slf4j
public class ModifyResponseBodyFilter implements GlobalFilter, Ordered {
    @Autowired
    private ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory;

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain chain) {
        GatewayFilter delegate = modifyResponseBodyGatewayFilterFactory.apply(new ModifyResponseBodyGatewayFilterFactory.Config()
                .setRewriteFunction(Object.class, Object.class, (exchange, result) -> {
                    try {
                        JsonElement jsonElement = JsonParser.parseString(new Gson().toJson(result));
                        if (jsonElement == null || !jsonElement.isJsonObject()) {
                            return Mono.just(result);
                        }
                        if (((JsonObject) jsonElement).get("code") == null) {
                            return Mono.just(result);
                        }
                        if (String.valueOf(ResultCode.SUCCESS_THEN_STORE_INFO.getCode()).equals(
                                ((JsonObject) jsonElement).get("code").getAsString())) {
                            SuccessWithExtraInfoResult<?> extraInfoResult = new Gson().fromJson(jsonElement, SuccessWithExtraInfoResult.class);

                            ServerHttpResponse response = exchange.getResponse();
                            extraInfoResult.getExtraInfo().forEach(info -> {
                                response.addCookie(generateCookie(info.getKey(), JSON.toJSONString(info.getValue()), info.getTtlSeconds()));
                            });
                            return Mono.just(CommonResult.success(extraInfoResult.getData(), extraInfoResult.getMessage()));
                        }
                        return Mono.just(result);
                    } catch (JSONException ignore) {
                        return Mono.just(result);
                    } catch (Exception e) {
                        log.error("result convert error, result: {}", result, e);
                        return Mono.just(CommonResult.failed(ResultCode.FAILED));
                    }
                }));
        return delegate.filter(serverWebExchange, chain);
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }

    private ResponseCookie generateCookie(String name, String value, long maxAge) {
        return ResponseCookie.from(name, value).maxAge(maxAge).httpOnly(true).secure(true).build();
    }

}
