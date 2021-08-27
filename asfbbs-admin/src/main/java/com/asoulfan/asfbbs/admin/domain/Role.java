package com.asoulfan.asfbbs.admin.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
@TableName("asf_role")
public class Role {

   private Long id;

    /**
     * 角色代码
     */
    @NotNull(message = "角色代码不能为空")
   private String code;

    /**
     * 角色名
     */
    @NotNull(message = "角色名不能为空")
    private String name;

    /**
     * 启用状态：0->禁用；1->启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;


}
