package com.asoulfan.asfbbs.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    private void deleteUserRoleByUserId(Long userId) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        userRoleMapper.delete(wrapper);
    }
}
