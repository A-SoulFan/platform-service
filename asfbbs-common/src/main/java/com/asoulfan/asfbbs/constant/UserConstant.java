package com.asoulfan.asfbbs.constant;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-28
 **/
public interface UserConstant {
    /**
     * redis 登录验证码前缀
     */
    String CAPT_REDIS_KEY = "user_capt_";

    /**
     * redis 答题时间前缀
     */
    String REGISTER_REDIS_KEY = "user_register_";

    /**
     * redis 可发送邮件时间前缀
     */
    String EMAIL_VALID_REDIS_KEY = "user_email_valid_";

    /**
     * redis email验证码
     */
    String EMAIL_REDIS_KEY = "user_email_code_";

    /**
     * redis 存储未完全注册用户信息
     */
    String USERINFO_REDIS_KEY="user_info_";
}
