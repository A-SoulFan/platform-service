package com.asoulfan.asfbbs.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * @program: ASFBBS
 * @description: 用户配置创建参数
 * @packagename: com.asoulfan.asfbbs.user.dto
 * @author: Cscar
 * @date: 2021-08-28 20:12
 **/
@Data
public class UserSettingUpdateParam {
    /**
     * 用户id
     */
    Long userId;

    /**
     * 要更新的配置选项
     */
    @NotEmpty
    Map<String, String> setting;

    /**
     * 生效的页面范围
     */
    @NotEmpty
    String scope;
}
