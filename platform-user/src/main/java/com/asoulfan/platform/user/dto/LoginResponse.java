package com.asoulfan.platform.user.dto;

import lombok.Data;

/**
 * @author fengling
 * @since 2021-08-28
 **/
@Data
public class LoginResponse {
    /**
     * 用户账号
     */
    private String userName;

    /**
     * 头像
     */
    private String icon;

    /**
     * 邮箱
     */
    private String nickname;

    /**
     * 绑定b站账号
     */
    private String bUid;
}
