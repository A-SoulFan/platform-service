package com.asoulfan.asfbbs.admin.service;

import com.asoulfan.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**

 * : 认证服务远程调用

 * @author Cscar
 * @since 2021-08-03 11:47
 */
@FeignClient("asfbbs-auth")
public interface AuthService {
    @PostMapping(value = "/oauth/token")
    CommonResult getAccessToken(@RequestParam Map<String, String> parameters);
}
