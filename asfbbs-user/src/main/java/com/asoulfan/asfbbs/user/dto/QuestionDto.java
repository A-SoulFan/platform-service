package com.asoulfan.asfbbs.user.dto;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * @author fengling
 * @since 2021-08-28
 **/
@Data
@TableName("asf_question")
public class QuestionDto {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    private String question;

    private String options;

    private String answer;

    private Integer tag;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("create_user")
    private String createUser;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("update_user")
    private String updateUser;
}
