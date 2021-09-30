package com.asoulfan.asfbbs.api;

/**
 * 封装API的错误码接口
 *
 * @author Cscar
 * @since 2021 -07-26 01:36
 */
public interface IErrorCode {

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    long getCode();

    /**
     * 获取消息体
     *
     * @return 消息体
     */
    String getMessage();
}
