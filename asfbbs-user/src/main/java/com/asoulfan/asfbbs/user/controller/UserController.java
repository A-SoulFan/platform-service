package com.asoulfan.asfbbs.user.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.asoulfan.asfbbs.api.CommonResult;
import com.asoulfan.asfbbs.exception.Asserts;
import com.asoulfan.asfbbs.user.domain.Oauth2TokenDto;
import com.asoulfan.asfbbs.user.dto.CaptVo;
import com.asoulfan.asfbbs.user.dto.FileVo;
import com.asoulfan.asfbbs.user.dto.LoginVo;
import com.asoulfan.asfbbs.user.dto.QuestionsVo;
import com.asoulfan.asfbbs.user.dto.RegisterVo;
import com.asoulfan.asfbbs.user.dto.ScoreVo;
import com.asoulfan.asfbbs.user.dto.UserDto;
import com.asoulfan.asfbbs.user.service.ICaptService;
import com.asoulfan.asfbbs.user.service.IIconService;
import com.asoulfan.asfbbs.user.service.IQuestionService;
import com.asoulfan.asfbbs.user.service.IUserService;

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

    @Autowired
    private IIconService iconService;

    /**
     * 用户登录
     *
     * @param vo
     * @param response
     * @return
     */
    @PostMapping("/login")
    public CommonResult<Oauth2TokenDto> login(@RequestBody @Validated LoginVo vo, HttpServletResponse response) {
        if (!captService.verify(vo.getCaptId(), vo.getCaptCode())) {
            Asserts.fail("验证码错误");
        }
        Oauth2TokenDto dto = userService.login(vo.getUsername(), vo.getPassword(), response);
        if (dto == null) {
            Asserts.fail("获取token失败");
        }
        return CommonResult.success(dto);
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @GetMapping("/getCapt")
    public CommonResult<CaptVo> getCapt() {
        return CommonResult.success(captService.getCapt());
    }

    /**
     * 用户注册时验证用户信息接口，校验通过后返回id作为后续注册流程的入参，由此来避免同时注册导致的用户信息被覆盖
     *
     * @param vo
     * @return
     */
    @PostMapping("/verifyUserInfo")
    public CommonResult<String> verifyUserInfo(@RequestBody RegisterVo vo) {
        UserDto userInfo = userService.getUserInfo(vo.getUsername());
        if (userInfo != null) {
            Asserts.fail("该用户已被注册");
        }
        return CommonResult.success(userService.verifyUserInfo(vo));
    }

    /**
     * 获取答题
     *
     * @param id verifyUserInfo接口返回的id
     * @return
     */
    @GetMapping("/question/list")
    public CommonResult<List<QuestionsVo>> getQuestions(@RequestParam("id") String id) {
        if (userService.isUserExist(id)) {
            Asserts.fail("该用户已被注册");
        }
        return CommonResult.success(questionService.getList(id));
    }

    /**
     * 校验答题得分
     *
     * @param vo
     * @return
     */
    @PostMapping("/question/score")
    public CommonResult<Boolean> score(@RequestBody ScoreVo vo) {
        if (userService.isUserExist(vo.getId())) {
            Asserts.fail("该用户已被注册");
        }
        return CommonResult.success(questionService.score(vo));
    }

    /**
     * 发送邮件
     *
     * @param email 邮箱地址
     * @param id    用户名称
     * @return
     */
    @GetMapping("/email")
    public CommonResult<Boolean> email(@RequestParam("email") @Email String email, @RequestParam("id") String id) {
        if (userService.isUserExist(id)) {
            Asserts.fail("该用户已被注册");
        }
        return CommonResult.success(userService.email(email, id));
    }

    /**
     * 验证邮箱验证码
     *
     * @param id   用户账号
     * @param code 邮箱验证号
     * @return
     */
    @GetMapping("/verify")
    public CommonResult<Boolean> verify(@RequestParam("id") String id, @RequestParam("code") String code) {
        if (userService.isUserExist(id)) {
            Asserts.fail("该用户已被注册");
        }
        return CommonResult.success(userService.verify(id, code));
    }

    /**
     * 绑定b站账号
     *
     * @param username 用户账号，后期优化后应该不需要传参数，直接从token中获取
     * @return
     */
    @GetMapping("/blbl/token")
    public CommonResult<String> getBiliToken(String username) {
        return CommonResult.success(userService.getBiliToken(username));
    }

    /**
     * 确认绑定
     *
     * @param username 用户账号，后期优化后应该不需要传参数，直接从token中获取
     * @return
     */
    @GetMapping("/blbl/confirm")
    public CommonResult<Boolean> confirm(String username) {
        return CommonResult.success(userService.confirm(username));
    }

    /**
     * 上传头像
     *
     * @param vo
     * @return 文件id，作为verifyUserInfo接口中的参数
     */
    @PostMapping("/icon/upload")
    public CommonResult<String> upload(@RequestBody FileVo vo) {
        return CommonResult.success(iconService.upload(vo.getImgByte()));
    }

    /**
     * 下载头像图片
     *
     * @param id 文件id
     */
    @GetMapping("/icon/{id}")
    public CommonResult<String> get(@PathVariable String id) {
        return CommonResult.success(iconService.get(id));
    }
}
