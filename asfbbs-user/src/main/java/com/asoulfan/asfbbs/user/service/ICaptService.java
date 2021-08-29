package com.asoulfan.asfbbs.user.service;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-28
 **/
public interface ICaptService {

    String getCapt(HttpServletResponse response);

    void verify(String id, String code);
}
