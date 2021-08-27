package com.asoulfan.asfbbs.user.service;


import com.asoulfan.asfbbs.user.domain.UserSetting;

public interface UserSettingService {
    public UserSetting getUserSetting(Long userId, String scope);
}
