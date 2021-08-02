package com.asoulfan.asfbbs.auth.service;

import com.asoulfan.asfbbs.domain.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: ASFBBS
 * @description: 用户服务
 * @packagename: com.asoulfan.asfbbs.service
 * @author: Cscar
 * @date: 2021-07-29 14:45
 **/
@FeignClient("asfbbs-portal")
public interface UmsMemberService {
    @GetMapping("/sso/loadByUsername")
    UserDto loadUserByUsername(@RequestParam String username);
}
