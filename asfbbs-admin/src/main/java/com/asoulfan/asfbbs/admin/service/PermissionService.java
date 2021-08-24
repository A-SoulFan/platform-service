package com.asoulfan.asfbbs.admin.service;

import java.util.List;

import com.asoulfan.asfbbs.admin.domain.Permission;

public interface PermissionService {

    List<Permission> findPermissions();

    void addPermission(Permission permission);

    void deletePermission(Long permissionId);

    void updatePermission(Permission permission);
}
