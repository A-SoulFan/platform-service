package com.asoulfan.asfbbs.admin.controller;

import java.util.List;
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
import com.asoulfan.asfbbs.admin.dto.ResetRolePermissionParam;
import com.asoulfan.asfbbs.admin.service.PermissionService;
import com.asoulfan.asfbbs.admin.service.RoleService;
import com.asoulfan.common.api.CommonResult;

/**
 * 角色管理
 *

 * : 角色管理

 */
@Controller
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    /**
     * 功能描述: 获取角色列表
     *
     * @param:
     * @return:
     * @author liurd
     * @since 2021/8/25
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Role>> findRoles() {
        return CommonResult.success(roleService.findRoles());
    }

    /**
     * 功能描述: 设置用户角色
     *
     * @param: userId roleIds
     * @return:
     * @author liurd
     * @since 2021/8/25
     */
    @RequestMapping(value = "/user/{userId}/reset_roles", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateUserRoles(@PathVariable Long userId, @Validated @RequestBody ResetRoleParam param) {
        roleService.resetUserRoles(userId, param.getRoleIds());
        return CommonResult.success("success");
    }

    /**
     * 功能描述：创建角色
     *
     * @param
     * @return:
     * @authorZGQ
     * @since 2021/8/27
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<?> addRole(@Validated @RequestBody Role role) {
        roleService.add(role);
        return CommonResult.success("添加成功");
    }

    /**
     * 功能描述: 删除已有角色
     *
     * @param: roleId
     * @return:
     * @author ZGQ
     * @since 2021/8/27
     */
    @RequestMapping(value = "/{roleId}/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public CommonResult delete(@PathVariable Long roleId) {
        roleService.delete(roleId);
        permissionService.pubPermissionInRedis();
        return CommonResult.success("success");

    }

    /**
     * 功能描述: 更新角色
     *
     * @param:
     * @return:
     * @author ZGQ
     * @since 2021/8/27
     */
    @RequestMapping(value = "/{roleId}/update", method = RequestMethod.PUT)
    @ResponseBody
    public CommonResult update(@PathVariable Long roleId, @Validated @RequestBody Role role) {
        role.setId(roleId);
        roleService.update(role);
        permissionService.pubPermissionInRedis();
        return CommonResult.success("success");
    }

    /**
     * 功能描述:通过id查询
     *
     * @param id
     * @return
     * @author ZGQ
     * @since 2021/8/24
     */
    @RequestMapping(value = "/queryById", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Role> queryById(@RequestParam Long id) {

        Role role = roleService.getById(id);
        if (role == null) {
            return CommonResult.failed("未找到对应实体");
        } else {
            return CommonResult.success(role);
        }
    }


    /**
     * 功能描述: 重新设置角色权限
     * @param: 
     * @return: 
     * @author liurd
     * @since 2021/8/27 
     */
    @RequestMapping(value = "/{roleId}/reset_permission", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult resetRolePermissions(@PathVariable Long roleId, @Validated @RequestBody ResetRolePermissionParam param) {
        permissionService.resetRolePermissions(roleId, param.getPermissionIds());
        return CommonResult.success("success");
    }

}
