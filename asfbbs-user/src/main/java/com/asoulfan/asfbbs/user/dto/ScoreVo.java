package com.asoulfan.asfbbs.user.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
     * 账号
     */
    @NotBlank
    private String username;

    /**
     * 回答
     */
    @NotNull
    private List<Answer> answers;
}
