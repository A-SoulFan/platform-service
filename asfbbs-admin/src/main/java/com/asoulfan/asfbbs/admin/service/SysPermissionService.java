package com.asoulfan.asfbbs.admin.service;

import com.asoulfan.asfbbs.admin.domain.SysPermission;

import java.util.List;

/**
 * @author ZGQ
 * @create 2021-08-25-14:19
 */
public interface SysPermissionService {
    List<SysPermission> getByUserId(String userId);

    Boolean pubPermissionInRedis();

}
