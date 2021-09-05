package com.asoulfan.asfbbs.user.dto;


import java.util.List;

import com.asoulfan.asfbbs.user.domain.Options;

import lombok.Data;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-28
 **/
@Data
public class QuestionsVo {

    /**
     * 问题id
     */
    private String id;

    /**
     * 题干
     */
    private String question;

    /**
     * 选项list
     */
    private List<Options> options;

}
