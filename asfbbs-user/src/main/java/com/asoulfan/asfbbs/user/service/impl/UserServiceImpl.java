package com.asoulfan.asfbbs.user.service.impl;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.asoulfan.asfbbs.api.CommonResult;
import com.asoulfan.asfbbs.api.ResultCode;
import com.asoulfan.asfbbs.constant.AuthConstant;
import com.asoulfan.asfbbs.constant.UserConstant;
import com.asoulfan.asfbbs.exception.Asserts;
import com.asoulfan.asfbbs.user.component.MyBCryptPasswordEncoder;
import com.asoulfan.asfbbs.user.domain.Oauth2TokenDto;
import com.asoulfan.asfbbs.user.dto.RegisterVo;
import com.asoulfan.asfbbs.user.dto.UserDto;
import com.asoulfan.asfbbs.user.mapper.UserMapper;
import com.asoulfan.asfbbs.user.service.AuthService;
import com.asoulfan.asfbbs.user.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.hutool.core.lang.Tuple;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
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

    private BCryptPasswordEncoder encoder = new MyBCryptPasswordEncoder();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 登录接口
     *
     * @return
     */
    @Override
    public Oauth2TokenDto login(String username, String password, HttpServletResponse response) {
        //1.生成token
        Map<String, String> params = new HashMap<>();
        params.put("client_id", AuthConstant.ADMIN_CLIENT_ID);
        params.put("client_secret", "asoul@fan12345");
        params.put("grant_type", "password");
        params.put("username", username);
        params.put("password", password);
        CommonResult<Oauth2TokenDto> restResult = authService.getAccessToken(params);
        if (ResultCode.SUCCESS.getCode() == restResult.getCode() && restResult.getData() != null) {
            return restResult.getData();
        } else {
            return null;
        }
    }

    /**
     * 注册接口
     *
     * @param vo
     * @return
     */
    @Override
    public String verifyUserInfo(RegisterVo vo) {
        vo.setPassword(encoder.encode(vo.getPassword()));
        UserDto newUser = new UserDto();
        BeanUtils.copyProperties(vo, newUser);
        String id = IdUtil.simpleUUID();
        //插入10min过期的tokenA，这段时间内用户可以重复答题
        redisTemplate.opsForValue().set(UserConstant.REGISTER_REDIS_KEY + id, "1", 10, TimeUnit.MINUTES);
        //插入30min过期的用户信息
        redisTemplate.opsForValue().set(UserConstant.USERINFO_REDIS_KEY + id, JSONUtil.toJsonStr(newUser), 30, TimeUnit.MINUTES);
        return id;
    }

    /**
     * 发送email接口
     *
     * @param email
     * @param id
     * @return
     */
    @Override
    public Boolean email(String email, String id) {
        Object o = redisTemplate.opsForValue().get(UserConstant.EMAIL_VALID_REDIS_KEY + id);
        if (o == null || !"1".equals(o.toString())) {
            Asserts.fail("已超过邮箱发送时限，本次注册失败");
        }
        String random = RandomStringUtils.random(4, "0123456789");
        Tuple tuple = new Tuple(email, random);
        redisTemplate.opsForValue().set(UserConstant.EMAIL_REDIS_KEY + id, JSONUtil.parse(tuple), 5, TimeUnit.MINUTES);
        String send = MailUtil.send(email, "验证码-ASOULFUN", id + "您好，您正在ASOULFUN绑定邮箱注册账号，本次操作的验证码为：" + random + "，请在5分钟内完成验证。：", false);
        return StrUtil.isNotBlank(send);
    }

    /**
     * 验证email接口
     *
     * @param id
     * @param code
     * @return
     */
    @Override
    public Boolean verify(String id, String code) {
        Object o = redisTemplate.opsForValue().get(UserConstant.EMAIL_REDIS_KEY + id);
        if (o == null) {
            Asserts.fail("验证码过期");
        }
        JSONArray result = JSONUtil.parseArray(o);
        if (!code.equals(result.get(1))) {
            Asserts.fail("验证码不正确");
        }
        Object userInfo = redisTemplate.opsForValue().get(UserConstant.USERINFO_REDIS_KEY + id);
        if (userInfo == null) {
            Asserts.fail("注册时间过长，请重新注册");
        }
        UserDto userDto = JSONUtil.toBean(userInfo.toString(), UserDto.class);
        userDto.setEmail(result.get(0).toString());
        return userMapper.insert(userDto) > 0;
    }

    /**
     * 获取bot token
     *
     * @param username
     * @return
     */
    @Override
    public String getBiliToken(String username) {
        UserDto userDto = getUserInfo(username);
        if (userDto == null) {
            Asserts.fail("用户信息有误");
        }
        if (StrUtil.isNotBlank(userDto.getBUid())) {
            Asserts.fail("用户已绑定b站账号");
        }
        String url = initTokenUrl + userDto.getId();
        String s = HttpUtil.get(url);
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

    /**
     * 确认b站绑定关系
     *
     * @param username
     * @return
     */
    @Override
    public Boolean confirm(String username) {
        UserDto userDto = userMapper.selectOne(new QueryWrapper<UserDto>().eq("username", username).eq("status", 1));
        if (userDto == null) {
            Asserts.fail("用户信息有误");
        }
        if (StrUtil.isNotBlank(userDto.getBUid())) {
            return true;
        }
        String url = confirmUrl + userDto.getId();
        String s = HttpUtil.get(url);
        if (StrUtil.isNotBlank(s)) {
            JSONObject jsonObject = JSONUtil.parseObj(s);
            Object bUid = jsonObject.get("bilibili_uid");
            if (bUid != null && StrUtil.isNotBlank(bUid.toString()) && !"0".equals(bUid.toString())) {
                userDto.setBUid(bUid.toString());
                return userMapper.updateById(userDto) > 0;
            }
        }
        Asserts.fail("绑定失败");
        return null;
    }

    @Override
    public UserDto getUserInfo(String username) {
        return userMapper.selectOne(new QueryWrapper<UserDto>()
                .eq("username", username)
                .eq("status", 1));
    }

    @Override
    public boolean isUserExist(String id) {
        Object o = redisTemplate.opsForValue().get(UserConstant.USERINFO_REDIS_KEY + id);
        if (o == null) {
            return false;
        }
        UserDto userDto = JSONUtil.toBean(o.toString(), UserDto.class);
        return getUserInfo(userDto.getUsername()) != null;
    }
}
