package com.asoulfan.asfbbs.user.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asoulfan.asfbbs.api.CommonResult;
import com.asoulfan.asfbbs.user.domain.Oauth2Token;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-28
 **/
@FeignClient("asfbbs-auth")
public interface AuthService {
    @PostMapping(value = "/oauth/token")
    CommonResult<Oauth2Token> getAccessToken(@RequestParam Map<String, String> parameters);
}