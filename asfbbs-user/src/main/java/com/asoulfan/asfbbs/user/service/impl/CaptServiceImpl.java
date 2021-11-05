package com.asoulfan.asfbbs.user.service.impl;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.asoulfan.asfbbs.user.dto.CaptVo;
import com.asoulfan.asfbbs.user.service.ICaptService;
import com.asoulfan.common.constant.UserConstant;
import com.asoulfan.common.exception.Asserts;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author fengling
 * @since 2021-08-28
 **/
@Service
@Slf4j
public class CaptServiceImpl implements ICaptService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public CaptVo getCapt() {
        CaptVo vo = new CaptVo();
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        vo.setImgByte(lineCaptcha.getImageBase64Data().replace("data:image/png;base64,", ""));
        String simpleId = IdUtil.simpleUUID();
        String a = UserConstant.CAPT_REDIS_KEY + simpleId;
        redisTemplate.opsForValue().set(a, lineCaptcha.getCode(), 1, TimeUnit.HOURS);
        vo.setCaptId(simpleId);
        return vo;
    }

    @Override
    public boolean verify(String id, String code) {
        Object o = redisTemplate.opsForValue().get(UserConstant.CAPT_REDIS_KEY + id);
        if (o == null) {
            Asserts.fail("验证码过期，请重新获取验证码");
        }
        return o.toString().equals(code);
    }
}
