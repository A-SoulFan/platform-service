package com.asoulfan.asfbbs.admin.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("asf_role_permission")
public class RolePermission {

    
   private Long id;

    /**
     * roleId
     */
   private Long roleId;

    /**
     * permissionId
     */
    private Long permissionId;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;


}
