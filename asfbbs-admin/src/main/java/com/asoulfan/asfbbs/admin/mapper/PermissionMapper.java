package com.asoulfan.asfbbs.admin.mapper;

import com.asoulfan.asfbbs.admin.domain.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZGQ
 * @create 2021-08-25-14:18
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> getByUserId(String userId);

    List<Permission> getListByRole(String roleName);
}
