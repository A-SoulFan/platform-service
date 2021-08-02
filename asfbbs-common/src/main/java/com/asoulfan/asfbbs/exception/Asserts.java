package com.asoulfan.asfbbs.exception;

import com.asoulfan.asfbbs.api.IErrorCode;

/**
 * @program: ASFBBS
 * @description: 断言处理类, 用来抛出各种API异常
 * @packagename: com.asoulfan.asfbbs.exception
 * @author: Cscar
 * @date: 2021-07-26 10:21
 **/
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
