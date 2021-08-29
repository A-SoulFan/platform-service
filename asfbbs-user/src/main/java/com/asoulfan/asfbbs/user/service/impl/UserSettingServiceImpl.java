package com.asoulfan.asfbbs.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.asoulfan.asfbbs.exception.Asserts;
import com.asoulfan.asfbbs.user.domain.UserSetting;
import com.asoulfan.asfbbs.user.dto.UserSettingUpdateParam;
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
        UserSetting userSetting = userSettingMapper.selectOne(wrapper);
        if (userSetting == null) {
            Asserts.fail("用户配置不存在");
        }
        return userSetting;

    }

    @Override
    public void updateUserSetting(UserSettingUpdateParam userSettingUpdateParam) {
        QueryWrapper<UserSetting> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userSettingUpdateParam.getUserId());
        wrapper.eq("scope", userSettingUpdateParam.getScope());
        UserSetting userSetting = userSettingMapper.selectOne(wrapper);
        if (userSetting == null) {
            Asserts.fail("该配置不存在");
        }
        JSONObject jsonObject = JSONObject.parseObject(userSetting.getSetting());
        jsonObject.putAll(userSettingUpdateParam.getSetting());
        userSetting.setSetting(jsonObject.toString());
        userSettingMapper.update(userSetting, wrapper);
    }

    @Override
    public void createUserSetting(UserSettingUpdateParam userSettingUpdateParam) {
        QueryWrapper<UserSetting> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userSettingUpdateParam.getUserId());
        wrapper.eq("scope", userSettingUpdateParam.getScope());
        if (userSettingMapper.selectCount(wrapper) > 0) {
            Asserts.fail("该配置已存在");
        }
        UserSetting userSetting = new UserSetting();
        userSetting.setUserId(userSettingUpdateParam.getUserId());
        userSetting.setSetting(JSONObject.toJSONString(userSettingUpdateParam.getSetting()));
        userSetting.setScope(userSettingUpdateParam.getScope());
        userSettingMapper.insert(userSetting);
    }
}
