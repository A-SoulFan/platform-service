package com.asoulfan.asfbbs.user.service.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.asoulfan.asfbbs.constant.UserConstant;
import com.asoulfan.asfbbs.exception.Asserts;
import com.asoulfan.asfbbs.user.service.ICaptService;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-28
 **/
@Service
@Slf4j
public class CaptServiceImpl implements ICaptService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public String getCapt(HttpServletResponse response) {
        String code = initCapt(response);
        if (StrUtil.isNotBlank(code)) {
            String simpleId = IdUtil.simpleUUID();
            redisTemplate.opsForValue().set(UserConstant.CAPT_REDIS_KEY + simpleId, code, 1, TimeUnit.MINUTES);
            return simpleId;
        }
        //TODO：抛出异常还需要return？
        Asserts.fail("生成验证码失败");
        return null;
    }

    private String initCapt(HttpServletResponse response) {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        try {
            lineCaptcha.write(response.getOutputStream());
        } catch (IOException ioException) {
            log.error("读取response io 失败，ex:" + ioException.getMessage());
            return null;
        }
        return lineCaptcha.getCode();
    }

    @Override
    public void verify(String id, String code) {
        Object o = redisTemplate.opsForValue().get(UserConstant.CAPT_REDIS_KEY + id);
        if (o == null) {
            Asserts.fail("验证码过期，请重新获取验证码");
        }
        if (!o.toString().equals(code)) {
            Asserts.fail("验证码不正确");
        }
    }
}
