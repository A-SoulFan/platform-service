package com.asoulfan.asfbbs.admin.service;


import java.util.List;
import java.util.Set;

import com.asoulfan.asfbbs.admin.domain.Permission;

/**
 * @author ZGQ
 * @create 2021-08-25-14:19
 */
public interface PermissionService {
    List<Permission> getByUserId(Long userId);

    Boolean pubPermissionInRedis();

    List<Permission> findPermissions();

    void addPermission(Permission permission);

    void deletePermission(Long permissionId);

    void updatePermission(Permission permission);

    void resetRolePermissions(Long roleId, Set<Long> permissionIds);
}
