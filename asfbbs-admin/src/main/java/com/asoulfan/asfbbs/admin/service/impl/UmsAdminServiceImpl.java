package com.asoulfan.asfbbs.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.asoulfan.asfbbs.admin.domain.UmsAdmin;
import com.asoulfan.asfbbs.admin.mapper.UmsAdminMapper;
import com.asoulfan.asfbbs.admin.service.UmsAdminService;
import com.asoulfan.asfbbs.domain.UserDto;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    private UmsAdminMapper umsAdminMapper;

    @Override
    public UserDto loadUserByUsername(String username) {
        /*QueryWrapper<UmsAdmin> qw = new QueryWrapper<>();
        QueryWrapper<UmsAdmin> umsAdminQueryWrapper = qw.eq("username", username);

        UmsAdmin umsAdmin = umsAdminMapper.selectOne(umsAdminQueryWrapper);*/
        UmsAdmin umsAdmin = umsAdminMapper.selectByUsername(username);
        if (umsAdmin != null) {
            UserDto userDTO = new UserDto();
            BeanUtils.copyProperties(umsAdmin,userDTO);
            return userDTO;
        }

        return null;
    }
}
