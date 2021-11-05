package com.asoulfan.asfbbs.user.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asoulfan.common.api.CommonResult;
import com.asoulfan.asfbbs.user.domain.Oauth2TokenDto;

/**
 * @author fengling
 * @since 2021-08-28
 **/
@FeignClient("asoul-fan-auth")
public interface AuthService {
    @PostMapping(value = "/oauth/token")
    CommonResult<Oauth2TokenDto> getAccessToken(@RequestParam Map<String, String> parameters);
}