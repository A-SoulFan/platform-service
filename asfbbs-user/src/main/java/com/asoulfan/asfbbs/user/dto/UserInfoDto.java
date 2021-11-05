package com.asoulfan.asfbbs.user.dto;

import java.util.List;

import com.asoulfan.asfbbs.user.domain.Permission;

import lombok.Data;

/**
 * 用户信息dto
 *
 * @author fengling
 * @since 2021-10-03
 **/
@Data
public class UserInfoDto {
    /**
     * 登录账号
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像图片id
     */
    private String icon;

    /**
     * 绑定b站id
     */
    private String bUid;

    /**
     * 权限列表
     */
    private List<Permission> permissionList;

    /**
     * 角色列表，例：5_超级管理员
     */
    private List<String> authorities;
}
