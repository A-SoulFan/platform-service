package com.asoulfan.asfbbs.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.asoulfan.asfbbs.admin.domain.Permission;
import com.asoulfan.asfbbs.admin.service.PermissionService;
import com.asoulfan.common.api.CommonResult;

/**
 * 角色管理
 *
 * @author ZGQ
 * @since 2021-08-24-13:15
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 查询用户拥有的菜单权限
     * param userId
     *
     * @return
     */
    @RequestMapping(value = "/getUserPermissionById", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Permission>> getUserPermissionByToken(@RequestParam("userId") Long userId) {
        List<Permission> permissionList = permissionService.getByUserId(userId);
        return CommonResult.success(permissionList);
    }

    /**
     * 将角色存权限存入redis
     * key :Role_User Role_Admin ...
     * value :permissionList
     */
    @RequestMapping(value = "/putPermissionInRedis", method = RequestMethod.PUT)
    @ResponseBody
    public CommonResult<?> putPermissionInRedis() {
        Boolean ok = permissionService.pubPermissionInRedis();
        if (ok) {
            return CommonResult.success("权限缓存redis成功");
        } else {
            return CommonResult.failed("权限缓存redis失败");
        }

    }

    /**
     * 功能描述:查询权限列表
     *
     * @param:
     * @return:
     * @author liurd
     * @since 2021/8/25
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Permission>> find() {
        return CommonResult.success(permissionService.findPermissions());
    }

    /**
     * 功能描述: 添加新权限
     *
     * @param:
     * @return:
     * @author liurd
     * @since 2021/8/25
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult add(@RequestBody Permission permission) {
        permissionService.addPermission(permission);
        return CommonResult.success("success");
    }

    /**
     * 功能描述: 删除已有权限
     *
     * @param: permissionId
     * @return:
     * @author liurd
     * @since 2021/8/25
     */
    @RequestMapping(value = "/{permissionId}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult delete(@PathVariable Long permissionId) {
        permissionService.deletePermission(permissionId);
        return CommonResult.success("success");

    }

    /**
     * 功能描述: 更新权限信息
     *
     * @param:
     * @return:
     * @author liurd
     * @since 2021/8/25
     */
    @RequestMapping(value = "/{permissionId}/update", method = RequestMethod.PUT)
    @ResponseBody
    public CommonResult update(@PathVariable Long permissionId, @RequestBody Permission permission) {
        permission.setId(permissionId);
        permissionService.updatePermission(permission);
        return CommonResult.success("success");
    }
}
