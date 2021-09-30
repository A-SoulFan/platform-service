package com.asoulfan.asfbbs.user.service;

import javax.servlet.http.HttpServletResponse;

import com.asoulfan.asfbbs.user.dto.CaptVo;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-28
 **/
public interface ICaptService {

    CaptVo getCapt();

    boolean verify(String id, String code);
}
