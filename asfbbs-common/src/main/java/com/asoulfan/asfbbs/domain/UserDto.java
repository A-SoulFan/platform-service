package com.asoulfan.asfbbs.domain;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 用户登录对象
 * fixme 用户领域对象迁到user模块内
 *
 * @author Cscar
 * @since 2021-07-27 11:28
 */
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
