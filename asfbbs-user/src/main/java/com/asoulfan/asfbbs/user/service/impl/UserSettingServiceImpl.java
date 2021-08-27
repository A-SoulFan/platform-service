package com.asoulfan.asfbbs.user.service.impl;

import com.asoulfan.asfbbs.user.domain.UserSetting;
import com.asoulfan.asfbbs.user.mapper.UserSettingMapper;
import com.asoulfan.asfbbs.user.service.UserSettingService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserSettingServiceImpl implements UserSettingService {
    @Resource
    private UserSettingMapper userSettingMapper;

    @Override
    public UserSetting getUserSetting(Long userId, String scope) {
        QueryWrapper<UserSetting> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("scope", scope);
        return userSettingMapper.selectOne(wrapper);
    }
}
