package com.asoulfan.asfbbs.auth.exception;

import com.asoulfan.asfbbs.api.CommonResult;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: ASFBBS
 * @description: 全局处理Oauth异常
 * @packagename: com.asoulfan.asfbbs.auth.exception
 * @author: Cscar
 * @date: 2021-07-29 17:58
 **/
@ControllerAdvice
public class Oauth2ExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = OAuth2Exception.class)
    public CommonResult handleOauth2(OAuth2Exception e) {
        return CommonResult.failed(e.getMessage());
    }
}
