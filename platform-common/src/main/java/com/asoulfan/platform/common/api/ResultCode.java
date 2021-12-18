package com.asoulfan.platform.common.api;

import lombok.Getter;

/**
 * 枚举常用API操作码
 *
 * @author Cscar
 * @since 2021 -07-26 01:37
 */
public enum ResultCode implements IErrorCode {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作成功并需要存储信息
     */
    SUCCESS_THEN_STORE_INFO(201, "操作成功并需要存储信息"),
    /**
     * 操作失败
     */
    FAILED(500, "操作失败"),
    /**
     * 参数检验失败
     */
    VALIDATE_FAILED(404, "参数检验失败"),
    /**
     * 暂未登录或token已经过期
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    /**
     * 没有相关权限
     */
    FORBIDDEN(403, "没有相关权限");

    @Getter
    private final long code;

    @Getter
    private final String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

}
