package com.asoulfan.asfbbs.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: ASFBBS
 * @description: 用户登录对象
 * @packagename: com.asoulfan.asfbbs.domain
 * @author: Cscar
 * @date: 2021-07-27 11:28
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class UserDto {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 角色列表
     */
    private List<String> roles;
}
