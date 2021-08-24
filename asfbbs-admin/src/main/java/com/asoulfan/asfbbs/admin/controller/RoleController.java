package com.asoulfan.asfbbs.admin.controller;

import java.util.List;
import java.util.Set;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asoulfan.asfbbs.admin.service.RoleService;
import com.asoulfan.asfbbs.api.CommonResult;


@Controller
@RequestMapping("/role")
public class RoleController {
    
    @Resource
    private RoleService roleService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult findRoles() {
        return CommonResult.success(roleService.findRoles());
    }

    @RequestMapping(value = "/user/{userId}/reset_roles", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateUserRoles(@PathVariable Long userId, @RequestBody Set<Long> roleIds) {
        roleService.resetUserRoles(userId, roleIds);
        return CommonResult.success("success");
    }
}
