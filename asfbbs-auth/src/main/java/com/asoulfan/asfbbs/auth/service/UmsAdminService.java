package com.asoulfan.asfbbs.auth.service;

import com.asoulfan.asfbbs.domain.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @program: ASFBBS
 * @description: 管理服务
 * @packagename: com.asoulfan.asfbbs.service.impl
 * @author: Cscar
 * @date: 2021-07-29 14:44
 **/
@FeignClient("asfbbs-admin")
public interface UmsAdminService {
    @GetMapping("/admin/loadByUsername")
    UserDto loadUserByUsername(@RequestParam String username);
}
