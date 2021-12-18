package com.asoulfan.platform.user.dto;

import java.util.List;

import com.asoulfan.platform.user.domain.Options;

import lombok.Data;

/**
 * @author fengling
 * @since 2021-08-28
 **/
@Data
public class QuestionsVo {

    /**
     * 问题id
     */
    private String questionId;

    /**
     * 题干
     */
    private String question;

    /**
     * 选项list
     */
    private List<Options> options;

}
