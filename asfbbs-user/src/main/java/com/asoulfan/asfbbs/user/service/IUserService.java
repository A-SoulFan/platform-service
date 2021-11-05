package com.asoulfan.asfbbs.user.service;

import javax.servlet.http.HttpServletResponse;

import com.asoulfan.asfbbs.user.domain.Oauth2TokenDto;
import com.asoulfan.asfbbs.user.dto.RegisterVo;
import com.asoulfan.asfbbs.user.dto.UserDto;
import com.asoulfan.asfbbs.user.dto.UserInfoDto;

/**
 * user接口
 *
 * @author fengling
 * @since 2021-08-27
 **/
public interface IUserService {
    Oauth2TokenDto login(String username, String password, HttpServletResponse response);

    String verifyUserInfo(RegisterVo vo);

    Boolean email(String email, String id);

    Boolean verify(String id, String code);

    String getBiliToken(String username);

    Boolean confirm(String username);

    UserDto getUserDto(String username);

    boolean isUserExist(String id);

    UserInfoDto getUserInfo(String username);
}