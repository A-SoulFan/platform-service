package com.asoulfan.asfbbs.admin.service.impl;

import com.asoulfan.asfbbs.admin.domain.SysPermission;
import com.asoulfan.asfbbs.admin.mapper.SysPermissionMapper;
import com.asoulfan.asfbbs.admin.service.SysPermissionService;
import com.asoulfan.asfbbs.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZGQ
 * @create 2021-08-25-14:19
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;
    @Autowired
    private RedisService redisService;
    @Override
    public List<SysPermission> getByUserId(String userId) {
        return sysPermissionMapper.getByUserId(userId);
    }

    @Override
    public Boolean pubPermissionInRedis() {
        //拿到数据库的所有角色
        //然后存入redis
        List<SysPermission> permissionList=sysPermissionMapper.getListByRole("");
        redisService.set("Role",permissionList);

        return true;
    }
}
