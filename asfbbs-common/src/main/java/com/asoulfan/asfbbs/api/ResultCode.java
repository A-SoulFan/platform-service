package com.asoulfan.asfbbs.api;

import lombok.Getter;

/**
 * 枚举常用API操作码
 *
 * @author Cscar
 * @since 2021 -07-26 01:37
 */
public enum ResultCode implements IErrorCode {
    /**
     * Success result code.
     */
    SUCCESS(200, "操作成功"),
    /**
     * Failed result code.
     */
    FAILED(500, "操作失败"),
    /**
     * Validate failed result code.
     */
    VALIDATE_FAILED(404, "参数检验失败"),
    /**
     * Unauthorized result code.
     */
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    /**
     * Forbidden result code.
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
