package com.asoulfan.platform.common.exception;

import com.asoulfan.platform.common.api.IErrorCode;

/**
 * 断言处理类, 用来抛出各种API异常
 *
 * @author Cscar
 * @since 2021-07-26 10:21
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
