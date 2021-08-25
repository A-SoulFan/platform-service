package com.asoulfan.asfbbs.admin.service.impl;

import com.asoulfan.asfbbs.admin.domain.Permission;
import com.asoulfan.asfbbs.admin.domain.Role;
import com.asoulfan.asfbbs.admin.domain.RolePermission;
import com.asoulfan.asfbbs.admin.mapper.PermissionMapper;
import com.asoulfan.asfbbs.admin.mapper.RolePermissionMapper;
import com.asoulfan.asfbbs.admin.service.PermissionService;
import com.asoulfan.asfbbs.admin.service.RoleService;
import com.asoulfan.asfbbs.constant.AuthConstant;
import com.asoulfan.asfbbs.exception.Asserts;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import cn.hutool.core.collection.CollUtil;

/**
 * @author ZGQ
 * @create 2021-08-25-14:19
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private PermissionMapper permissionMapper;

    @Resource
    private RolePermissionMapper rolePermissionMapper;
    
    @Resource
    private RoleService roleService;
    
    
    @Override
    public List<Permission> getByUserId(String userId) {
        return permissionMapper.getByUserId(userId);
    }

    @Override
    @PostConstruct
    public Boolean pubPermissionInRedis() {
        //拿到数据库的所有角色
        //然后存入redis
        //auth:resourceRolesMap
        List<Role> roles = roleService.findRoles();
        Map<String, String> rolesMap = new HashMap<>();
        for (Role role : roles) {
            List<Permission> permissions = this.findPermissionsByRole(role.getId());
            permissions.stream().filter(i -> i.getUrl() != null)
                    .forEach(i -> rolesMap.put(i.getUrl(), role.getCode()));
        }

        redisTemplate.opsForHash().putAll(AuthConstant.RESOURCE_ROLES_MAP_KEY,  rolesMap);

        return true;
    }

    private List<Permission> findPermissionsByRole(Long roleId) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        List<RolePermission> rolePermissions = rolePermissionMapper.selectList(wrapper);
        Set<Long> permissionIds = rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toSet());
        if (CollUtil.isEmpty(permissionIds)) {
            return new ArrayList<>();
        }
        return permissionMapper.selectBatchIds(permissionIds);
    }

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