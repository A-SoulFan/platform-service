package com.asoulfan.asfbbs.admin.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("asf_user_role")
public class UserRole {

   private Long id;

    /**
     * userId
     */
   private Long userId;

    /**
     * roleId
     */
    private Long roleId;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;


}
