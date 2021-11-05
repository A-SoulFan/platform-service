package com.asoulfan.asfbbs.user.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * 用户验证信息入参
 *
 * @author fengling
 * @since 2021-08-28
 **/
@Data
public class RegisterVo {

    /**
     * 用户账号
     */
    @NotBlank(message = "用户账号不能为空")
    @Length(min = 5, max = 19, message = "用户账号名限制在5-19字符之间")
    private String username;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    private String nickname;

    /**
     * 密码
     */
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 6, max = 50, message = "密码长度限制在6-50字符之间")
    private String password;

    // /**
    //  * 头像
    //  */
    // @NotBlank(message = "用户头像不能为空")
    // private String icon;

}
