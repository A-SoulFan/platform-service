package com.asoulfan.asfbbs.user.service;


import com.asoulfan.asfbbs.user.domain.UserSetting;
import com.asoulfan.asfbbs.user.dto.UserSettingUpdateParam;

public interface UserSettingService {
    UserSetting getUserSetting(Long userId, String scope);

    void updateUserSetting(UserSettingUpdateParam userSettingUpdateParam);

    void createUserSetting(UserSettingUpdateParam userSettingUpdateParam);
}
