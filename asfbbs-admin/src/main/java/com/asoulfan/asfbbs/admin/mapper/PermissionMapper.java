package com.asoulfan.asfbbs.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.asoulfan.asfbbs.admin.domain.Permission;
import com.asoulfan.asfbbs.admin.domain.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    
    
}
