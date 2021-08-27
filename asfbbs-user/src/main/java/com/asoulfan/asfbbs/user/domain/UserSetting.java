package com.asoulfan.asfbbs.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
@TableName("asf_user_setting")
public class UserSetting {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)
    @JsonIgnore
    private Long id;

    /**
     * userId
     */
    private Long userId;

    /**
     * 用户配置
     */
    private String setting;

    /**
     * 页面范围
     */
    private String scope;
}
