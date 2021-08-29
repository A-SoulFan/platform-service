package com.asoulfan.asfbbs.user.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.asoulfan.asfbbs.api.CommonResult;
import com.asoulfan.asfbbs.api.ResultCode;
import com.asoulfan.asfbbs.constant.AuthConstant;
import com.asoulfan.asfbbs.constant.UserConstant;
import com.asoulfan.asfbbs.exception.Asserts;
import com.asoulfan.asfbbs.user.component.MyBCryptPasswordEncoder;
import com.asoulfan.asfbbs.user.dto.LoginResponse;
import com.asoulfan.asfbbs.user.domain.Oauth2Token;
import com.asoulfan.asfbbs.user.dto.UserDto;
import com.asoulfan.asfbbs.user.dto.RegisterVo;
import com.asoulfan.asfbbs.user.mapper.UserMapper;
import com.asoulfan.asfbbs.user.service.AuthService;
import com.asoulfan.asfbbs.user.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.lang.Tuple;
import cn.hutool.core.math.MathUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

/**
 * @program: ASFBBS
 * @description: userService实现类
 * @packagename: com.asoulfan.asfbbs.user.service.impl
 * @author: fengling
 * @date: 2021-08-27
 **/
@Service
public class UserServiceImpl implements IUserService {

    @Value("${b-bot.token}")
    private String initTokenUrl;

    @Value("${b-bot.confirm}")
    private String confirmUrl;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private AuthService authService;

    //TODO: 使用security自带的加密方式

    // @Resource
    // private BCryptPasswordEncoder encoder = new MyBCryptPasswordEncoder();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 登录接口
     *
     * @return
     */
    @Override
    public LoginResponse login(String username, String password, HttpServletResponse response) {
        password = SecureUtil.md5(password);
        //1.校验密码
        UserDto userDto = userMapper.selectOne(
                new QueryWrapper<UserDto>()
                        .eq("username", username)
                        .eq("password", password)
                        .eq("status", 0));
        if (userDto == null) {
            Asserts.fail("用户名或密码错误");
        }
        //2.生成token
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.ADMIN_CLIENT_ID);
        params.put("client_secret", "asoul@fan12345");
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        CommonResult<Oauth2Token> restResult = authService.getAccessToken(params);
        if (ResultCode.SUCCESS.getCode() == restResult.getCode() && restResult.getData() != null) {
            Cookie cookie = new Cookie("token", JSONUtil.toJsonStr(restResult.getData()));
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            Asserts.fail("获取token失败");
        }
        LoginResponse loginResponse = new LoginResponse();
        BeanUtils.copyProperties(userDto, loginResponse);
        return loginResponse;
    }

    @Override
    public Boolean register(RegisterVo vo) {
        //TODO:重复注册问题
        UserDto userDto = userMapper.selectOne(new QueryWrapper<UserDto>()
                .eq("username", vo.getUsername())
                .eq("status", 0));
        if (userDto != null) {
            Asserts.fail("用户名已被注册");
        }
        vo.setPassword(SecureUtil.md5(vo.getPassword()));
        UserDto newUser = new UserDto();
        BeanUtils.copyProperties(vo, newUser);
        //插入10min过期的tokenA，这段时间内用户可以重复答题
        redisTemplate.opsForValue().set(UserConstant.REGISTER_REDIS_KEY + vo.getUsername(), "1", 10, TimeUnit.MINUTES);
        //插入30min过期的用户信息
        redisTemplate.opsForValue().set(UserConstant.USERINFO_REDIS_KEY + vo.getUsername(), JSONUtil.toJsonStr(newUser), 30, TimeUnit.MINUTES);
        return true;

    }

    @Override
    public Boolean email(String email, String username) {
        Object o = redisTemplate.opsForValue().get(UserConstant.EMAIL_VALID_REDIS_KEY + username);
        if (o == null || !"1".equals(o.toString())) {
            Asserts.fail("已超过邮箱发送时限，本次注册失败");
        }
        String random = RandomStringUtils.random(4, "0123456789");
        Tuple tuple = new Tuple(email, random);
        redisTemplate.opsForValue().set(UserConstant.EMAIL_REDIS_KEY + username, JSONUtil.parse(tuple), 2, TimeUnit.MINUTES);
        //TODO:邮件文本格式
        String send = MailUtil.send(email, "获取验证码", "本次注册验证码为：" + random, false);
        return StrUtil.isNotBlank(send);
    }

    @Override
    public Boolean verify(String username, String code) {
        Object o = redisTemplate.opsForValue().get(UserConstant.EMAIL_REDIS_KEY + username);
        if (o == null) {
            Asserts.fail("验证码过期");
        }
        JSONArray result = JSONUtil.parseArray(o);
        if (!code.equals(result.get(1))) {
            Asserts.fail("验证码不正确");
        }
        Object userInfo = redisTemplate.opsForValue().get(UserConstant.USERINFO_REDIS_KEY + username);
        if (userInfo == null) {
            Asserts.fail("注册时间过长，请重新注册");
        }
        UserDto userDto = JSONUtil.toBean(userInfo.toString(), UserDto.class);
        userDto.setEmail(result.get(0).toString());
        return userMapper.insert(userDto) > 0;
    }

    @Override
    public String getBiliToken(String username) {
        UserDto userDto = userMapper.selectOne(new QueryWrapper<UserDto>().eq("username", username).eq("status", 0));
        if (userDto == null) {
            Asserts.fail("用户信息有误");
        }
        if (StrUtil.isNotBlank(userDto.getBUid())) {
            Asserts.fail("用户已绑定b站账号");
        }
        String s = HttpUtil.get(initTokenUrl + userDto.getId());
        if (StrUtil.isNotBlank(s)) {
            JSONObject jsonObject = JSONUtil.parseObj(s);
            Object token = jsonObject.get("token");
            if (token != null && StrUtil.isNotBlank(token.toString())) {
                return token.toString();
            }
        }
        Asserts.fail("获取b站token失败");
        return null;
    }

    @Override
    public Boolean confirm(String username) {
        UserDto userDto = userMapper.selectOne(new QueryWrapper<UserDto>().eq("username", username).eq("status", 0));
        if (userDto == null) {
            Asserts.fail("用户信息有误");
        }
        if (StrUtil.isNotBlank(userDto.getBUid())) {
            return true;
        }
        String s = HttpUtil.get(confirmUrl + userDto.getId());
        if (StrUtil.isNotBlank(s)) {
            JSONObject jsonObject = JSONUtil.parseObj(s);
            Object bUid = jsonObject.get("bilibili_uid");
            if (bUid != null && StrUtil.isNotBlank(bUid.toString())) {
                userDto.setBUid(bUid.toString());
                return userMapper.updateById(userDto) > 0;
            }
        }
        Asserts.fail("绑定失败");
        return null;
    }
}
