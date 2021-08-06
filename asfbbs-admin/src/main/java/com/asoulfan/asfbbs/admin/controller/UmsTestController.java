package com.asoulfan.asfbbs.admin.controller;

import com.asoulfan.asfbbs.api.CommonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: ASFBBS
 * @description: 测试
 * @packagename: com.asoulfan.asfbbs.admin.controller
 * @author: Cscar
 * @date: 2021-08-03 12:01
 **/
@Controller
@RequestMapping("/test")
public class UmsTestController {

    @RequestMapping(value = "/test1", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult test1() {
        return CommonResult.success("test123");
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult info() {
        return CommonResult.success("哈哈哈");
    }
}
