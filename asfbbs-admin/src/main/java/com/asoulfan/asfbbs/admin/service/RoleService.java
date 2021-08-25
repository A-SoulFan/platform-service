package com.asoulfan.asfbbs.admin.service;

import java.util.List;
import java.util.Set;

import com.asoulfan.asfbbs.admin.domain.Role;
import com.asoulfan.asfbbs.api.CommonResult;

public interface RoleService {

    List<Role> findRoles();

    void resetUserRoles(Long userId, Set<Long> roleIds);
}
