package com.asoulfan.asfbbs.user.domain;

import lombok.Data;

/**
 * @program: ASFBBS
 * @description: user实体类
 * @packagename: com.asoulfan.asfbbs.user.domain
 * @author: fengling
 * @date: 2021-08-27
 **/

@Data
public class User {

    private String userName;

    private String nickName;

    private String password;

    private String icon;

    private String email;

    private String bUid;

}
