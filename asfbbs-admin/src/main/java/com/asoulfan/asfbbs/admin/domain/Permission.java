package com.asoulfan.asfbbs.admin.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("asf_permission")
public class Permission {

    
   private Long id;

    /**
     * 权限代码
     */
   private String code;

    /**
     * 权限说明
     */
    private String desc;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;


}
