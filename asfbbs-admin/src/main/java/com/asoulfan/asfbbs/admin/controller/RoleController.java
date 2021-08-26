package com.asoulfan.asfbbs.admin.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asoulfan.asfbbs.admin.domain.Role;
import com.asoulfan.asfbbs.admin.dto.ResetRoleParam;
import com.asoulfan.asfbbs.admin.service.RoleService;
import com.asoulfan.asfbbs.api.CommonResult;

/**
 * 角色管理
 *
 * @program: ASFBBS
 * @description: 角色管理
 * @packagename: com.asoulfan.asfbbs.admin
 **/
@Controller
@RequestMapping("/role")
public class RoleController {
    
    @Resource
    private RoleService roleService;

    /**
     * 功能描述: 获取角色列表
     * @param: 
     * @return: 
     * @author: liurd
     * @date: 2021/8/25 
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Role>> findRoles() {
        return CommonResult.success(roleService.findRoles());
    }

    /**
     * 功能描述: 设置用户角色
     * @param: userId roleIds
     * @return: 
     * @author: liurd
     * @date: 2021/8/25 
     */
    @RequestMapping(value = "/user/{userId}/reset_roles", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateUserRoles(@PathVariable Long userId, @Validated @RequestBody ResetRoleParam param) {
        roleService.resetUserRoles(userId, param.getRoleIds());
        return CommonResult.success("success");
    }
}
