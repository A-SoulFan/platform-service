package com.asoulfan.platform.user.domain;

import lombok.Data;

/**
 * @author fengling
 * @since 2021-08-28
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
