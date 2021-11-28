package com.asoulfan.asfbbs.user.domain;

import lombok.Data;

/**
 * user实体类
 * @author fengling
 * @since 2021-08-27
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
