package com.asoulfan.asfbbs.admin.controller;

import com.asoulfan.asfbbs.admin.service.UmsAdminService;
import com.asoulfan.asfbbs.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private UmsAdminService adminService;

    /**
     * 根据用户名获取通用用户信息
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/loadByUsername", method = RequestMethod.GET)
    @ResponseBody
    public UserDto loadUserByUsername(@RequestParam String username) {
        UserDto userDTO = adminService.loadUserByUsername(username);
        return userDTO;
    }
}
