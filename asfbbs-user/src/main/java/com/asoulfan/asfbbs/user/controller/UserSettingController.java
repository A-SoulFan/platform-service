package com.asoulfan.asfbbs.user.controller;

import com.asoulfan.asfbbs.api.CommonResult;
import com.asoulfan.asfbbs.user.dto.UserSettingUpdateParam;
import com.asoulfan.asfbbs.user.service.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @program: ASFBBS
 * @description: 用户配置
 * @packagename: com.asoulfan.asfbbs.user.controller
 * @author: 飞飞飞
 * @date: 2021-08-28 00:28
 **/
@Controller
@RequestMapping("/setting")
public class UserSettingController {

    @Autowired
    UserSettingService userSettingService;

    /**
     * 获取用户配置
     * @param userId 用户id
     * @param scope 生效的页面范围
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getUserSetting(Long userId, String scope) {
        return CommonResult.success(userSettingService.getUserSetting(userId, scope));
    }

    /**
     * 更新用户配置
     * @param userSettingUpdateParam
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateUserSetting(@Validated @RequestBody UserSettingUpdateParam userSettingUpdateParam) {
        userSettingService.updateUserSetting(userSettingUpdateParam);
        return CommonResult.success("success");
    }

    /**
     * 创建用户配置
     * @param userSettingUpdateParam
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createUserSetting(@Validated @RequestBody UserSettingUpdateParam userSettingUpdateParam) {
        userSettingService.createUserSetting(userSettingUpdateParam);
        return CommonResult.success("success");
    }
}
