package com.asoulfan.asfbbs.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.asoulfan.asfbbs.user.domain.Permission;
import com.asoulfan.common.api.CommonResult;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-10-03
 **/
@FeignClient("asfbbs-admin")
public interface AdminService {
    @GetMapping(value = "/permission/getUserPermissionById")
    CommonResult<List<Permission>> getUserPermissionById(@RequestParam Long userId);
}
