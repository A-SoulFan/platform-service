package com.asoulfan.asfbbs.user.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asoulfan.asfbbs.api.CommonResult;
import com.asoulfan.asfbbs.user.dto.LoginResponse;
import com.asoulfan.asfbbs.user.dto.LoginVo;
import com.asoulfan.asfbbs.user.dto.QuestionsVo;
import com.asoulfan.asfbbs.user.dto.RegisterVo;
import com.asoulfan.asfbbs.user.dto.ScoreVo;
import com.asoulfan.asfbbs.user.service.ICaptService;
import com.asoulfan.asfbbs.user.service.IQuestionService;
import com.asoulfan.asfbbs.user.service.IUserService;

import cn.hutool.core.util.StrUtil;

/**
 * @program: ASFBBS
 * @description: user 控制层
 * @packagename: com.asoulfan.asfbbs.user.controller
 * @author: fengling
 * @date: 2021-08-27
 **/

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICaptService captService;

    @Autowired
    private IQuestionService questionService;

    /**
     * 用户登录
     * @param vo
     * @param response
     * @return
     */
    @PostMapping("/login")
    public CommonResult<LoginResponse> login(@RequestBody LoginVo vo, HttpServletResponse response) {
        captService.verify(vo.getCaptId(), vo.getCaptCode());
        return CommonResult.success(userService.login(vo.getUsername(), vo.getPassword(), response));
    }

    /**
     * 获取验证码
     * @param response
     * @return
     */
    @GetMapping("/getCapt")
    public CommonResult<String> getCapt(HttpServletResponse response) {
        return CommonResult.success(captService.getCapt(response));
    }

    /**
     * 用户注册
     * @param vo
     * @return
     */
    @PostMapping("/register")
    public CommonResult<Boolean> register(@RequestBody RegisterVo vo) {
        return CommonResult.success(userService.register(vo));
    }

    /**
     * 获取答题
     *
     * @param username 用户账号
     * @return
     */
    @GetMapping("/question/list")
    public CommonResult<List<QuestionsVo>> getQuestions(@RequestParam("username") String username) {
        return CommonResult.success(questionService.getList(username));
    }

    /**
     * 校验答题得分
     *
     * @param vo
     * @return
     */
    @PostMapping("/question/score")
    public CommonResult<Boolean> score(@RequestBody ScoreVo vo) {
        return CommonResult.success(questionService.score(vo));
    }

    /**
     * 发送邮件
     *
     * @param email    邮箱地址
     * @param username 用户名称
     * @return
     */
    @GetMapping("/email")
    public CommonResult<Boolean> email(@RequestParam("email") @Email String email, @RequestParam("username") String username) {
        return CommonResult.success(userService.email(email, username));
    }

    /**
     * 验证邮箱验证码
     *
     * @param username 用户账号
     * @param code     邮箱验证号
     * @return
     */
    @GetMapping("/verify")
    public CommonResult<Boolean> verify(@RequestParam("username") String username, @RequestParam("code") String code) {
        return CommonResult.success(userService.verify(username, code));
    }

    /**
     * 绑定b站账号
     *
     * @param username 用户账号
     * @return
     */
    @GetMapping("/blbl/token")
    public CommonResult<String> getBiliToken(String username) {
        return CommonResult.success(userService.getBiliToken(username));
    }

    /**
     * 确认绑定
     *
     * @param username 用户账号
     * @return
     */
    @GetMapping("/blbl/confirm")
    public CommonResult<Boolean> confirm(String username) {
        return CommonResult.success(userService.confirm(username));
    }
}
