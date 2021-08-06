package com.asoulfan.asfbbs.admin.controller;

import com.asoulfan.asfbbs.admin.dto.UmsAdminLoginParam;
import com.asoulfan.asfbbs.admin.service.UmsAdminService;
import com.asoulfan.asfbbs.api.CommonResult;
import com.asoulfan.asfbbs.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 后台用户管理
 *
 * @program: ASFBBS
 * @description: 后台用户管理
 * @packagename: com.asoulfan.asfbbs.admin
 * @author: Cscar
 * @date: 2021-08-01 03:05
 **/
@Controller
@RequestMapping("/admin")
public class UmsAdminController {

    @Autowired
    private UmsAdminService umsAdminService;


    /**
     * 登录接口
     *
     * @param umsAdminLoginParam
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(@Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam) {
        return umsAdminService.login(umsAdminLoginParam.getUsername(),umsAdminLoginParam.getPassword());
    }

    /**
     * 根据用户名获取通用用户信息
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/loadByUsername", method = RequestMethod.GET)
    @ResponseBody
    public UserDto loadUserByUsername(@RequestParam String username) {
        UserDto userDTO = umsAdminService.loadUserByUsername(username);
        return userDTO;
    }
}
