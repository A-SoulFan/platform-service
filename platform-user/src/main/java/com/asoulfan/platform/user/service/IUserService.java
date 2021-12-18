package com.asoulfan.platform.user.service;

import javax.servlet.http.HttpServletResponse;

import com.asoulfan.platform.user.domain.LoginToken;
import com.asoulfan.platform.user.dto.RegisterVo;
import com.asoulfan.platform.user.dto.UserDto;
import com.asoulfan.platform.user.dto.UserInfoDto;

/**
 * user接口
 *
 * @author fengling
 * @since 2021-08-27
 **/
public interface IUserService {
    LoginToken login(String username, String password, HttpServletResponse response);

    UserDto queryUser(String username);

    String verifyUserInfo(RegisterVo vo);

    Boolean email(String email, String id);

    Boolean verify(String id, String code);

    String getBiliToken(String username);

    Boolean confirm(String username);

    UserDto getUserDto(String username);

    boolean isUserExist(String id);

    UserInfoDto getUserInfo(String username);
}