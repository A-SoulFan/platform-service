package com.asoulfan.platform.user.service;

import com.asoulfan.platform.user.dto.CaptVo;

/**
 * @author fengling
 * @since 2021-08-28
 **/
public interface ICaptService {

    CaptVo getCapt();

    boolean verify(String id, String code);
}
