package com.asoulfan.asfbbs.api;

/**
 * @program: asfbbs
 * @description: 枚举常用API操作码
 * @packagename: com.asoulfan.asfbbs.api
 * @author: Cscar
 * @date: 2021-07-26 01:37
 **/
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
