package com.asoulfan.asfbbs.auth.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asoulfan.common.domain.UserDto;

/**
 * 管理服务
 *
 * @author Cscar
 * @since 2021-07-29 14:44
 */
@FeignClient("asoul-fan-admin")
public interface UmsAdminService {
    @GetMapping("/admin/loadByUsername")
    UserDto loadUserByUsername(@RequestParam String username);
}
