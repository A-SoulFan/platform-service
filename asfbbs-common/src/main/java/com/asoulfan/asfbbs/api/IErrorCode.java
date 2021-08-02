package com.asoulfan.asfbbs.api;

/**
 * @program: asfbbs
 * @description: 封装API的错误码
 * @packagename: com.asoulfan.asfbbs.api
 * @author: Cscar
 * @date: 2021-07-26 01:36
 **/
public interface IErrorCode {

    long getCode();

    String getMessage();
}
