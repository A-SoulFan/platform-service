package com.asoulfan.asfbbs.auth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asoulfan.common.domain.UserDto;

/**
 * 用户服务
 *
 * @author Cscar
 * @since 2021-07-29 14:45
 */
@FeignClient("asfbbs-portal")
public interface UmsMemberService {
    @GetMapping("/sso/loadByUsername")
    UserDto loadUserByUsername(@RequestParam String username);
}
