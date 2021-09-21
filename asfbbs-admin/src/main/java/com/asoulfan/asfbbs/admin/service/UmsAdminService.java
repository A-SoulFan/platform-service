package com.asoulfan.asfbbs.admin.service;

import com.asoulfan.asfbbs.api.CommonResult;
import com.asoulfan.asfbbs.domain.UserDto;

/**

 * : 后台管理服务

 * @author Cscar
 * @since 2021-08-01 03:10
 */
public interface UmsAdminService {

    UserDto loadUserByUsername(String username);

    CommonResult login(String username, String password);

}
