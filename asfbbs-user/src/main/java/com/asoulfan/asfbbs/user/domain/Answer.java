package com.asoulfan.asfbbs.user.domain;

import lombok.Data;

/**
 * @program: ASFBBS
 * @description:
 * @packagename: com.asoulfan.asfbbs.user
 * @author: fengling
 * @date: 2021-08-28
 **/
@Data
public class Answer {
    /**
     * 问题id
     */
    private String questionId;

    /**
     * 答案
     */
    private String answer;
}
