package com.asoulfan.asfbbs.admin.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.asoulfan.asfbbs.admin.domain.Role;
import com.asoulfan.asfbbs.admin.domain.UmsAdmin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * @Author ZGQ
     * @Date
     * : 删除角色与用户关系
     */
    @Delete("delete from asf_user_role where role_id = #{roleId}")
    void deleteRoleUserRelation(@Param("roleId") Long roleId);

    /**
     * @Author ZGQ
     * @Date
     * : 删除角色与权限关系
     */
    @Delete("delete from asf_role_permission where role_id = #{roleId}")
    void deleteRolePermissionRelation(@Param("roleId") Long roleId);
}
