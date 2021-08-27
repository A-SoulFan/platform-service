package com.asoulfan.asfbbs.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asoulfan.asfbbs.admin.domain.Role;
import com.asoulfan.asfbbs.admin.domain.UserRole;
import com.asoulfan.asfbbs.admin.mapper.RoleMapper;
import com.asoulfan.asfbbs.admin.mapper.UserRoleMapper;
import com.asoulfan.asfbbs.admin.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

@Service
public class RoleServiceImpl implements RoleService {



    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> findRoles() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        return roleMapper.selectList(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetUserRoles(Long userId, Set<Long> roleIds) {
        this.deleteUserRoleByUserId(userId);
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRoleMapper.insert(userRole);
        }   
    }

    @Override
    public Set<Long> findRoleIdByUserId(Long userId) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<UserRole> userRoles = userRoleMapper.selectList(wrapper);
        return userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet());
    }

    @Override
    public void add(Role role) {
         roleMapper.insert(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long roleId) {
        //1.删除角色和用户关系
        roleMapper.deleteRoleUserRelation(roleId);
        //2.删除角色和权限关系
        roleMapper.deleteRolePermissionRelation(roleId);
        this.delete(roleId);
    }

    @Override
    public void update(Role role) {
        roleMapper.updateById(role);
    }

    @Override
    public Role getById(String id) {
        return roleMapper.selectById(id);
    }

    private void deleteUserRoleByUserId(Long userId) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        userRoleMapper.delete(wrapper);
    }
}
