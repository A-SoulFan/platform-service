package com.asoulfan.asfbbs.user.controller;

import com.asoulfan.asfbbs.api.CommonResult;
import com.asoulfan.asfbbs.user.service.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getUserSetting(Long userId, String scope) {
        return CommonResult.success(userSettingService.getUserSetting(userId, scope));
    }
}
