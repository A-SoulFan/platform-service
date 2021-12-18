package com.asoulfan.platform.user.service;

/**
 * 头像相关
 *
 * @author fengling
 * @since 2021-09-05
 **/
public interface IIconService {
    String upload(String imgByte);

    String get(String id);
}
