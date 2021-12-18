package com.asoulfan.platform.user.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asoulfan.platform.common.api.CommonResult;
import com.asoulfan.platform.user.domain.LoginToken;

/**
 * @author fengling
 * @since 2021-08-28
 **/
@FeignClient("asoul-fan-auth")
public interface AuthService {
    @PostMapping(value = "/oauth/token")
    CommonResult<LoginToken> getAccessToken(@RequestParam Map<String, String> parameters);
}