package com.asoulfan.asfbbs.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.asoulfan.asfbbs.admin.domain.UmsAdmin;
import com.asoulfan.asfbbs.admin.mapper.UmsAdminMapper;
import com.asoulfan.asfbbs.admin.service.AuthService;
import com.asoulfan.asfbbs.admin.service.UmsAdminService;
import com.asoulfan.asfbbs.api.CommonResult;
import com.asoulfan.asfbbs.api.ResultCode;
import com.asoulfan.asfbbs.constant.AuthConstant;
import com.asoulfan.asfbbs.domain.UserDto;
import com.asoulfan.asfbbs.exception.Asserts;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.admin.service.impl
 * @author: Cscar
 * @date: 2021-08-01 03:11
 **/
@Service
public class UmsAdminServiceImpl implements UmsAdminService {

    @Autowired
    private AuthService authService;

    @Autowired
    private UmsAdminMapper umsAdminMapper;

    @Override
    public UserDto loadUserByUsername(String username) {
        /*QueryWrapper<UmsAdmin> qw = new QueryWrapper<>();
        QueryWrapper<UmsAdmin> umsAdminQueryWrapper = qw.eq("username", username);

        UmsAdmin umsAdmin = umsAdminMapper.selectOne(umsAdminQueryWrapper);*/
        UmsAdmin umsAdmin = umsAdminMapper.selectByUsername(username);
        if (umsAdmin != null) {
            List<String> rolesList = new ArrayList<>();
            rolesList.add("5_超级管理员");

            UserDto userDTO = new UserDto();
            BeanUtils.copyProperties(umsAdmin, userDTO);
            userDTO.setRoles(rolesList);
            return userDTO;
        }

        return null;
    }

    @Override
    public CommonResult login(String username, String password) {

        if (StrUtil.isEmpty(username) || StrUtil.isEmpty(password)) {
            Asserts.fail("用户名或密码不能为空！");
        }
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.ADMIN_CLIENT_ID);
        params.put("client_secret", "asoul@fan12345");
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        CommonResult restResult = authService.getAccessToken(params);

        if (ResultCode.SUCCESS.getCode() == restResult.getCode() && restResult.getData() != null) {
        }
        return restResult;
    }


}
