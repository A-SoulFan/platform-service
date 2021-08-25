package com.asoulfan.asfbbs.admin.mapper;

import com.asoulfan.asfbbs.admin.domain.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZGQ
 * @create 2021-08-25-14:18
 */
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    List<SysPermission> getByUserId(String userId);

    List<SysPermission> getListByRole(String roleName);
}
