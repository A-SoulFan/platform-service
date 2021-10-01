package com.asoulfan.asfbbs.factory;


import java.util.List;
import java.util.Set;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.MessageBodyDecoder;
import org.springframework.cloud.gateway.filter.factory.rewrite.MessageBodyEncoder;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.http.ResponseCookie;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.asoulfan.asfbbs.api.CommonResult;
import com.asoulfan.asfbbs.api.ResultCode;
import com.asoulfan.asfbbs.domain.Oauth2TokenDto;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
@Slf4j
/**
 * @program: ASFBBS
 * @description: 返回体修改类
 * @packagename: com.asoulfan.asfbbs.factory
 * @author: sensen
 * @date: 2021-09-30
 **/
public class ResponseModifyGatewayFilterFactory extends ModifyResponseBodyGatewayFilterFactory {
    public ResponseModifyGatewayFilterFactory(List<HttpMessageReader<?>> messageReaders, Set<MessageBodyDecoder> messageBodyDecoders, Set<MessageBodyEncoder> messageBodyEncoders) {
        super( messageReaders, messageBodyDecoders, messageBodyEncoders);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return new ModifyResponseGatewayFilter(this.getConfig());
    };

    /***
     * Config.setRewriteFunction(Class<T> inClass, Class<R> outClass, RewriteFunction<T, R> rewriteFunction)
     * inClass 原数据类型。
     * outClass 目标数据类型
     * rewriteFunction 内容重写方法
     * @return
     */
    private Config getConfig() {
        Config cf = new Config();
        cf.setRewriteFunction(Object.class, CommonResult.class, getRewriteFunction());
        return cf;
    }

    private RewriteFunction<Object, CommonResult> getRewriteFunction() {
        return (exchange, s) ->{
            try{
                String result = JSONObject.toJSONString(s);
                CommonResult commonResult =JSONObject.parseObject(result,CommonResult.class);
                if (ResultCode.SUCCESS.getCode() == commonResult.getCode()){
                    Oauth2TokenDto oauth2TokenDto = JSONObject.toJavaObject((JSON) commonResult.getData(),Oauth2TokenDto.class);
                    ServerHttpResponse response = exchange.getResponse();
                    response.addCookie(generateCookie("accessToken", oauth2TokenDto.getToken()));
                    response.addCookie(generateCookie("refreshToken", oauth2TokenDto.getRefreshToken()));
                    response.addCookie(generateCookie("tokenHead", oauth2TokenDto.getTokenHead().trim()));
                    response.addCookie(generateCookie("expiresIn", String.valueOf(oauth2TokenDto.getExpiresIn())));
                    return Mono.just(CommonResult.success(true));
                }
                return Mono.just(new CommonResult(commonResult.getCode(),commonResult.getMessage(),false));
            }
            catch (Exception e){
                log.error("login controller error " + e.getMessage());
                return Mono.just(CommonResult.failed(ResultCode.FAILED));
            }
        };
    }

    private ResponseCookie generateCookie(String name,String value){
        return ResponseCookie.from( name,value).httpOnly(true).secure(true).build();
    }


}