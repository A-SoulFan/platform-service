package com.asoulfan.asfbbs.admin.domain;

import lombok.Data;

import java.util.Date;

/**
 * @program: ASFBBS
 * @description: 管理员用户
 * @packagename: com.asoulfan.asfbbs.admin.domain
 * @author: Cscar
 * @date: 2021-08-01 03:43
 **/
@Data
public class UmsAdmin {

    private Long id;

    private String username;

    private String password;

    /**
     * 头像
     */
    private String icon;

    /**
     * 邮箱
     */
    private String email;

    // /**
    //  * 备注信息
    //  */
    // private String note;

    /**
     * 创建时间
     */
    private Date createTime;

    // /**
    //  * 最后登录时间
    //  */
    // private Date loginTime;

    /**
     * 帐号启用状态：0->禁用；1->启用
     */
    private Integer status;
}
