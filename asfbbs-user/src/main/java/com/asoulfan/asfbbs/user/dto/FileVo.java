package com.asoulfan.asfbbs.user.dto;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-09-30
 **/
@Data
public class FileVo {
    /**
     头像最大允许5M，5*1024*1024/0.75(base64比原文件大1/3)
     */
    @Length(max = 6990507, message = "图片大小不得超过5M")
    private String imgByte;
}
