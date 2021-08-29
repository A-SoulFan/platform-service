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
    Long userId;

    @NotEmpty
    Map<String, String> setting;

    @NotEmpty
    String scope;
}
