package com.asoulfan.platform.user.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author fengling
 * @since 2021-08-28
 **/
@Data
@Builder
public class LoginToken {

    /**
     * 访问令牌
     */
    private String token;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 访问令牌头前缀
     */
    private String tokenHead;

    /**
     * 有效时间（秒）
     */
    private int expiresIn;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;
}
