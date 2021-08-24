package com.asoulfan.asfbbs.admin.service.impl;

import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asoulfan.asfbbs.admin.domain.Permission;
import com.asoulfan.asfbbs.admin.domain.RolePermission;
import com.asoulfan.asfbbs.admin.domain.UserRole;
import com.asoulfan.asfbbs.admin.mapper.PermissionMapper;
import com.asoulfan.asfbbs.admin.mapper.RolePermissionMapper;
import com.asoulfan.asfbbs.admin.service.PermissionService;
import com.asoulfan.asfbbs.exception.ApiException;
import com.asoulfan.asfbbs.exception.Asserts;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<Permission> findPermissions() {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        return permissionMapper.selectList(wrapper);
    }

    @Override
    public void addPermission(Permission permission) {
        Permission dbVal = null;
        if (permission.getUrl() != null) {
            dbVal = this.getPermissionByUrl(permission.getUrl());

        }else if (permission.getComponent() != null) {
            dbVal = this.getPermissionByComponent(permission.getComponent());
        }
        if (dbVal != null) {
            Asserts.fail("权限已存在！");
        }
        permissionMapper.insert(permission);
    }

    private Permission getPermissionByComponent(String component) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("component", component);
        return permissionMapper.selectOne(wrapper);
    }

    private Permission getPermissionByUrl(String url) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("url", url);
        return permissionMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePermission(Long permissionId) {
        permissionMapper.deleteById(permissionId);
        
        this.deleteRolePermissionByPermissionId(permissionId);
    }

    private void deleteRolePermissionByPermissionId(Long permissionId) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("permission_id", permissionId);
        rolePermissionMapper.delete(wrapper);
    }

    @Override
    public void updatePermission(Permission permission) {
        permissionMapper.updateById(permission);
    }
}
