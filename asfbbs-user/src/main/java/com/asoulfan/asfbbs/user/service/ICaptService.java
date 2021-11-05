package com.asoulfan.asfbbs.user.service;

import com.asoulfan.asfbbs.user.dto.CaptVo;

/**
 * @author fengling
 * @since 2021-08-28
 **/
public interface ICaptService {

    CaptVo getCapt();

    boolean verify(String id, String code);
}
