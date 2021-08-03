package com.asoulfan.asfbbs.admin.service;

import com.asoulfan.asfbbs.api.CommonResult;
import com.asoulfan.asfbbs.domain.UserDto;

/**
 * @program: ASFBBS
 * @description: 后台管理服务
 * @packagename: com.asoulfan.asfbbs.admin.service
 * @author: Cscar
 * @date: 2021-08-01 03:10
 **/
public interface UmsAdminService {

    UserDto loadUserByUsername(String username);

    CommonResult login(String username, String password);

}
