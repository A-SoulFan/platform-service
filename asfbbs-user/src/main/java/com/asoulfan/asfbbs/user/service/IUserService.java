package com.asoulfan.asfbbs.user.service;

import javax.servlet.http.HttpServletResponse;

import com.asoulfan.asfbbs.user.dto.LoginResponse;
import com.asoulfan.asfbbs.user.dto.RegisterVo;

/**
 * @program: ASFBBS
 * @description: user接口
 * @packagename: com.asoulfan.asfbbs.user.service
 * @author: fengling
 * @date: 2021-08-27
 **/
public interface IUserService {
    LoginResponse login(String username, String password, HttpServletResponse response);

    Boolean register(RegisterVo vo);

    Boolean email(String email, String username);

    Boolean verify(String username, String code);

    String getBiliToken(String username);

    Boolean confirm(String username);
}