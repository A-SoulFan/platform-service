package com.asoulfan.platform.user.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * @author fengling
 * @since 2021-08-27
 **/
@Data
public class LoginVo {
    /**
     * 用户账号
     */
    @NotBlank(message = "用户账号不能为空")
    @Length(min = 5, max = 19, message = "用户账号名限制在5-19字符之间")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "用户密码不能为空")
    @Length(min = 6, max = 50, message = "密码长度限制在6-50字符之间")
    private String password;

    // /**
    //  * 验证码图片id
    //  */
    // @NotBlank(message = "验证码图片id不能为空")
    // @Length(min = 32, max = 32, message = "非法验证码图片id")
    // private String captId;
    //
    // /**
    //  * 验证码
    //  */
    // @NotBlank(message = "验证码不能为空")
    // @Length(min = 5, max = 5, message = "非法验证码")
    //     private String captCode;

}
