package com.asoulfan.asfbbs.user.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.asoulfan.asfbbs.user.domain.Answer;

import lombok.Data;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-28
 **/
@Data
public class ScoreVo {

    /**
     * 注册用户唯一id
     */
    @NotBlank(message = "用户id不能为空")
    private String id;

    /**
     * 回答
     */
    @NotNull(message = "答案不能为空")
    private List<Answer> answers;
}
