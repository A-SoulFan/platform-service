package com.asoulfan.asfbbs.auth.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @program: ASFBBS
 * @description: Oauth获取token返回信息
 * @packagename: com.asoulfan.asfbbs.auth.domain
 * @author: Cscar
 * @date: 2021-07-29 16:04
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Oauth2TokenDto {

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

}
