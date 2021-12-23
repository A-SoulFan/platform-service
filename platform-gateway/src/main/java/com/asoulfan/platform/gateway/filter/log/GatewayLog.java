package com.asoulfan.platform.gateway.filter.log;

import java.util.Date;

import org.springframework.cloud.gateway.route.Route;

import lombok.Data;

/**
 * @author asuka
 * @date 2021/12/18
 */
@Data
public class GatewayLog {
    /**
     * 用户
     */
    private String user;

    /**
     * 请求路径
     */
    private String requestPath;

    /**
     * 请求方法
     */
    private String requestMethod;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 协议
     */
    private String schema;

    /**
     * 请求体
     */
    private String requestBody;

    /**
     * 路由路径
     */
    private Route route;

    /**
     * 响应的状态码
     */
    private Integer responseStatus;

    /**
     * 响应类型
     */
    private String responseType;

    /**
     * 响应体
     */
    private String responseData;

    /**
     * 请求ip
     */
    private String ip;

    /**
     * 请求时间
     */
    private Date requestTime;

}
