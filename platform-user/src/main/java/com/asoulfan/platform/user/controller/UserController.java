package com.asoulfan.platform.user.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.asoulfan.platform.user.common.JwtComponent;
import com.asoulfan.platform.user.domain.LoginToken;
import com.asoulfan.platform.user.service.ICaptService;
import com.asoulfan.platform.user.service.IIconService;
import com.asoulfan.platform.user.service.IQuestionService;
import com.asoulfan.platform.user.service.IUserService;
import com.asoulfan.platform.user.dto.FileVo;
import com.asoulfan.platform.user.dto.LoginVo;
import com.asoulfan.platform.user.dto.RegisterVo;
import com.asoulfan.platform.user.dto.UserDto;
import com.asoulfan.platform.common.api.CommonResult;
import com.asoulfan.platform.common.constant.AuthConstant;
import com.asoulfan.platform.common.domain.UserJwtDto;
import com.asoulfan.platform.common.exception.Asserts;
import com.google.gson.Gson;

import cn.hutool.json.JSONUtil;

/**
 * user 控制层
 *
 * @author fengling
 * @since 2021-08-27
 **/
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private ICaptService captService;

    @Autowired
    private IQuestionService questionService;

    @Autowired
    private IIconService iconService;

    @Autowired
    private JwtComponent jwtComponent;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /**
     * 用户登录
     *
     * @param vo
     * @param response
     * @return
     */
    @PostMapping("/login")
    public CommonResult<LoginToken> login(@RequestBody LoginVo vo, HttpServletResponse response) {
        // if (!captService.verify(vo.getCaptId(), vo.getCaptCode())) {
        //     Asserts.fail("验证码错误");
        // }

        UserDto user = userService.queryUser(vo.getUsername());
        if (user == null) {
            log.error("用户不存在，登录信息: {}", new Gson().toJson(vo));
            Asserts.fail("用户名或密码错误");
        }

        if (!encoder.matches(vo.getPassword(), user.getPassword())) {
            log.error("密码错误，请重试尝试登录失败，登录信息: {}", new Gson().toJson(vo));
            Asserts.fail("用户名或密码错误");
        }

        String jwt = jwtComponent.generateJwt(user);

        LoginToken token = LoginToken.builder()
                .expiresIn(-1)
                .token(jwt)
                .userId(user.getId())
                .nickname(user.getNickname())
                .avatar(user.getIcon())
                .build();

        return CommonResult.success(token);
    }

    // /**
    //  * 获取验证码
    //  *
    //  * @return
    //  */
    // @GetMapping("/getCapt")
    // public CommonResult<CaptVo> getCapt() {
    //     return CommonResult.success(captService.getCapt());
    // }

    /**
     * 用户注册时验证用户信息接口
     *
     * @param vo
     * @return
     */
    @PostMapping("/verifyUserInfo")
    public CommonResult<String> verifyUserInfo(@RequestBody RegisterVo vo) {
        UserDto userInfo = userService.getUserDto(vo.getUsername());
        if (userInfo != null) {
            Asserts.fail("该用户已被注册");
        }
        return CommonResult.success(userService.verifyUserInfo(vo));
    }

    // /**
    //  * 获取答题
    //  *
    //  * @param id verifyUserInfo接口返回的用户唯一id
    //  * @return
    //  */
    // @GetMapping("/question/list")
    // public CommonResult<List<QuestionsVo>> getQuestions(@RequestParam("id") @NotBlank(message = "用户注册id不能为空") String id) {
    //     if (userService.isUserExist(id)) {
    //         Asserts.fail("该用户已被注册");
    //     }
    //     return CommonResult.success(questionService.getList(id));
    // }
    //
    // /**
    //  * 校验答题得分
    //  *
    //  * @param vo
    //  * @return
    //  */
    // @PostMapping("/question/score")
    // public CommonResult<Boolean> score(@RequestBody ScoreVo vo) {
    //     if (userService.isUserExist(vo.getId())) {
    //         Asserts.fail("该用户已被注册");
    //     }
    //     return CommonResult.success(questionService.score(vo));
    // }

    /**
     * 发送邮件
     *
     * @param email 邮箱地址
     * @param id    verifyUserInfo接口返回的id
     * @return
     */
    @GetMapping("/email")
    public CommonResult<Boolean> email(
            @RequestParam("email") @Email(message = "非法邮件格式") String email,
            @RequestParam("id") @NotBlank(message = "用户注册id不能为空") String id) {
        if (userService.isUserExist(id)) {
            Asserts.fail("该用户已被注册");
        }
        return CommonResult.success(userService.email(email, id));
    }

    /**
     * 验证邮箱验证码
     *
     * @param id   verifyUserInfo接口返回的id
     * @param code 邮箱验证码
     * @return
     */
    @GetMapping("/verify")
    public CommonResult<Boolean> verify(@RequestParam("id") @NotBlank(message = "用户注册id不能为空") String id,
            @RequestParam("code") @NotBlank(message = "邮箱验证码不能为空") String code) {
        if (userService.isUserExist(id)) {
            Asserts.fail("该用户已被注册");
        }
        return CommonResult.success(userService.verify(id, code));
    }

    /**
     * 绑定b站账号
     *
     * @return
     */
    @GetMapping("/blbl/token")
    public CommonResult<String> getBiliToken(@RequestHeader(value = AuthConstant.USER_TOKEN_HEADER) String userStr) {
        UserJwtDto dto = JSONUtil.toBean(userStr, UserJwtDto.class);
        return CommonResult.success(userService.getBiliToken(dto.getUserName()));
    }

    /**
     * 确认绑定
     *
     * @return
     */
    @GetMapping("/blbl/confirm")
    public CommonResult<Boolean> confirm(@RequestHeader(value = AuthConstant.USER_TOKEN_HEADER) String userStr) {
        UserJwtDto dto = JSONUtil.toBean(userStr, UserJwtDto.class);
        return CommonResult.success(userService.confirm(dto.getUserName()));
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

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/getUserInfo")
    public CommonResult<UserDto> getUserInfo(@CookieValue(name = "asoulFanToken") String token) {
        UserDto userDto = jwtComponent.verify(token);
        return CommonResult.success(userDto);
    }
}
