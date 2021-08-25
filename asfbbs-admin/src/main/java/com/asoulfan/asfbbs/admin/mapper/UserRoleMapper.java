package com.asoulfan.asfbbs.admin.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.asoulfan.asfbbs.admin.domain.Role;
import com.asoulfan.asfbbs.admin.domain.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {
    
    
}
