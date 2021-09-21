package com.asoulfan.asfbbs.auth.service;

import com.asoulfan.asfbbs.domain.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**

 * : 用户服务

 * @author Cscar
 * @since 2021-07-29 14:45
 */
@FeignClient("asfbbs-portal")
public interface UmsMemberService {
    @GetMapping("/sso/loadByUsername")
    UserDto loadUserByUsername(@RequestParam String username);
}
