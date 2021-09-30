package com.asoulfan.asfbbs.user.dto;

import lombok.Data;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-09-30
 **/
@Data
public class CaptVo {
    /**
     验证码图片base64
     */
    private String imgByte;

    /**
     验证码图片id
     */
    private String captId;
}
