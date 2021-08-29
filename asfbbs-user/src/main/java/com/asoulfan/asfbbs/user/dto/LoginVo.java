package com.asoulfan.asfbbs.user.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NonNull;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-27
 **/
@Data
public class LoginVo {
    /**
     * 用户账号
     */
    @NotBlank
    private String username;

    /**
     * 密码
     */
    @NotBlank
    private String password;

    /**
     * 验证码图片id
     */
    @NotBlank
    private String captId;

    /**
     * 验证码
     */
    @NotBlank
    private String captCode;
}
