package com.asoulfan.platform.gateway.filter.log;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.gateway.support.ipresolver.XForwardedRemoteAddressResolver;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;

import com.asoulfan.platform.common.nacos.NacosValue;
import com.asoulfan.platform.gateway.common.GatewayConstants;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author asuka
 * @date 2021/12/18
 */
@Component
@Slf4j
public class LogFilter implements GlobalFilter, Ordered {
    @NacosValue(group = GatewayConstants.NACOS_GROUP_ID, dataId = "logConfig")
    private LogConfig logConfig = new LogConfig();

    private final List<HttpMessageReader<?>> messageReaders = HandlerStrategies.withDefaults().messageReaders();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // ????????????
        String requestPath = request.getPath().pathWithinApplication().value();
        String scheme = request.getURI().getScheme();
        String ipAddress = getIp(exchange);
        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

        GatewayLog gatewayLog = new GatewayLog();
        gatewayLog.setSchema(scheme);
        gatewayLog.setRequestPath(requestPath);
        gatewayLog.setRequestMethod(request.getMethodValue());
        gatewayLog.setRequestType(Optional.ofNullable(exchange.getRequest().getHeaders().getContentType()).map(MimeType::toString).orElse(""));
        gatewayLog.setRoute(route);
        gatewayLog.setRequestTime(new Date());
        gatewayLog.setIp(ipAddress);

        if (!"http".equals(scheme) && !"https".equals(scheme)) {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> writeGatewayLog(gatewayLog)));
        }

        MediaType mediaType = request.getHeaders().getContentType();

        if (MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType) || MediaType.APPLICATION_JSON.isCompatibleWith(mediaType)) {
            return writeBodyLog(exchange, chain, gatewayLog);
        } else {
            return writeBasicLog(exchange, chain, gatewayLog);
        }
    }

    @Override
    public int getOrder() {
        return -2;
    }

    private String getIp(ServerWebExchange exchange) {
        XForwardedRemoteAddressResolver resolver = XForwardedRemoteAddressResolver.maxTrustedIndex(1);
        InetSocketAddress inetSocketAddress = resolver.resolve(exchange);
        return Optional.ofNullable(inetSocketAddress).map(InetSocketAddress::getAddress).map(InetAddress::getHostAddress).orElse("");
    }

    private Mono<Void> writeBasicLog(ServerWebExchange exchange, GatewayFilterChain chain, GatewayLog gatewayLog) {
        StringBuilder builder = new StringBuilder();
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
            builder.append(entry.getKey()).append("=").append(StringUtils.join(entry.getValue(), ","));
        }
        gatewayLog.setRequestBody(builder.toString());

        //???????????????
        ServerHttpResponseDecorator decoratedResponse = recordResponseLog(exchange, gatewayLog);

        return chain.filter(exchange.mutate().response(decoratedResponse).build()).then(Mono.fromRunnable(() -> writeGatewayLog(gatewayLog)));
    }

    /**
     * ?????? request body ???????????????????????????
     * ??????: org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory
     */
    private Mono<Void> writeBodyLog(ServerWebExchange exchange, GatewayFilterChain chain, GatewayLog gatewayLog) {
        ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);

        Mono<String> modifiedBody = serverRequest.bodyToMono(String.class).flatMap(body -> {
            gatewayLog.setRequestBody(body);
            return Mono.just(body);
        });

        // ?????? BodyInserter ?????? body(????????????body), ?????? request body ??????????????????
        BodyInserter<Mono<String>, ReactiveHttpOutputMessage> bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(exchange.getRequest().getHeaders());
        // the new content type will be computed by bodyInserter
        // and then set in the request decorator
        headers.remove(HttpHeaders.CONTENT_LENGTH);

        CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);

        return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
            // ??????????????????
            ServerHttpRequest decoratedRequest = requestDecorate(exchange, headers, outputMessage);
            // ??????????????????
            ServerHttpResponseDecorator decoratedResponse = recordResponseLog(exchange, gatewayLog);

            // ???????????????
            return chain.filter(exchange.mutate().request(decoratedRequest).response(decoratedResponse).build()).then(Mono.fromRunnable(() -> {
                // ????????????
                writeGatewayLog(gatewayLog);
            }));
        }));
    }

    /**
     * ?????????????????????????????? headers
     */
    private ServerHttpRequestDecorator requestDecorate(ServerWebExchange exchange, HttpHeaders headers, CachedBodyOutputMessage outputMessage) {
        return new ServerHttpRequestDecorator(exchange.getRequest()) {
            @Override
            public HttpHeaders getHeaders() {
                long contentLength = headers.getContentLength();
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.putAll(super.getHeaders());
                if (contentLength > 0) {
                    httpHeaders.setContentLength(contentLength);
                } else {
                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                }
                return httpHeaders;
            }

            @Override
            public Flux<DataBuffer> getBody() {
                return outputMessage.getBody();
            }
        };
    }

    /**
     * ??????????????????
     * ?????? DataBufferFactory ????????????????????????????????????
     */
    private ServerHttpResponseDecorator recordResponseLog(ServerWebExchange exchange, GatewayLog gatewayLog) {
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();

        return new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                String originalResponseContentType = exchange.getAttribute(ServerWebExchangeUtils.ORIGINAL_RESPONSE_CONTENT_TYPE_ATTR);
                gatewayLog.setResponseStatus(Optional.ofNullable(this.getStatusCode()).map(HttpStatus::value).orElse(-1));
                gatewayLog.setResponseType(originalResponseContentType);

                if (body instanceof Flux) {
                    // ?????????????????????????????? json ?????????
                    if (ObjectUtil.equal(this.getStatusCode(), HttpStatus.OK) && StringUtils.isNotBlank(originalResponseContentType) && originalResponseContentType.contains("application/json")) {

                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {

                            // ???????????????????????????????????????????????????
                            DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                            DataBuffer join = dataBufferFactory.join(dataBuffers);
                            byte[] content = new byte[join.readableByteCount()];
                            join.read(content);

                            // ???????????????
                            DataBufferUtils.release(join);
                            String responseResult = new String(content, StandardCharsets.UTF_8);

                            gatewayLog.setResponseData(responseResult);

                            return bufferFactory.wrap(content);
                        }));
                    }
                }
                return super.writeWith(body);
            }
        };
    }

    /**
     * ????????????
     */
    private void writeGatewayLog(GatewayLog gatewayLog) {
        if (logConfig.isLogEnable()) {
            StringBuilder sb = new StringBuilder("[Gateway]");
            sb.append("ip: ").append(gatewayLog.getIp()).append(", ")
                .append(gatewayLog.getRequestMethod()).append(" ").append(gatewayLog.getRequestPath()).append(", ");
            if (logConfig.isLogRoute()) {
                sb.append("route to: ").append(gatewayLog.getRoute().getUri()).append(", ");
            }
            if (logConfig.isLogUser()) {
                sb.append("user: ").append(gatewayLog.getUser()).append(", ");
            }
            if (logConfig.isLogRequest()) {
                sb.append("requestType: ").append(gatewayLog.getRequestType()).append(",")
                    .append("requestBody: ").append(gatewayLog.getRequestBody()).append(", ");
            }
            if (logConfig.isLogResponse()) {
                sb.append("responseStatus: ").append(gatewayLog.getResponseStatus()).append(",")
                    .append("responseType: ").append(gatewayLog.getResponseType()).append(",")
                    .append("responseData: ").append(gatewayLog.getResponseData()).append(", ");
            }
            sb.append("costTime: ").append(System.currentTimeMillis() - gatewayLog.getRequestTime().getTime()).append("ms");
            log.info(sb.toString());
        }
    }
}
