package com.asoulfan.asfbbs.admin.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asoulfan.asfbbs.admin.domain.Permission;
import com.asoulfan.asfbbs.admin.service.PermissionService;
import com.asoulfan.asfbbs.api.CommonResult;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    
    @Resource
    private PermissionService permissionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult find() {
        return CommonResult.success(permissionService.findPermissions());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@RequestBody Permission permission) {
        permissionService.addPermission(permission);
        return CommonResult.success("success");
    }

    @RequestMapping(value = "/{permissionId}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult delete(@PathVariable Long permissionId) {
        permissionService.deletePermission(permissionId);
        return CommonResult.success("success");

    }

    @RequestMapping(value = "/{permissionId}/update", method = RequestMethod.PUT)
    @ResponseBody
    public CommonResult update(@PathVariable Long permissionId, @RequestBody Permission permission) {
        permission.setId(permissionId);
        permissionService.updatePermission(permission);
        return CommonResult.success("success");
    }
}
