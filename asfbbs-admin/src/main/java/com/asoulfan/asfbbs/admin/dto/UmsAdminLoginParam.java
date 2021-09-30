package com.asoulfan.asfbbs.admin.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**

 * : 用户登录参数

 * @author Cscar
 * @since 2021-08-03 11:44
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UmsAdminLoginParam {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
