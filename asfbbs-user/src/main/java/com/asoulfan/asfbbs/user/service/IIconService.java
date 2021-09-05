package com.asoulfan.asfbbs.user.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

/**
 * @program: ASFBBS
 * @description: 头像相关
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-09-05
 **/
public interface IIconService {
    String upload(MultipartFile file);

    void get(String id, HttpServletResponse response);
}
