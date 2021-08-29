package com.asoulfan.asfbbs.user.dto;

import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-27
 **/
@Data
@TableName("asf_user")
public class UserDto {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("username")
    private String username;

    @TableField("nickname")
    private String nickname;

    @TableField("password")
    private String password;

    @TableField("icon")
    private String icon;

    @TableField("email")
    private String email;

    @TableField("b_uid")
    private String bUid;

    @TableField("status")
    private Integer status;

    @TableField("create_time")
    private LocalDate createTime;

    @TableField("update_time")
    private LocalDate updateTime;

}
